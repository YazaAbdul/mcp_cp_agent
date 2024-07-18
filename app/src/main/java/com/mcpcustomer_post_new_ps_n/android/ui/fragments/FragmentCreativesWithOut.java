package com.mcpcustomer_post_new_ps_n.android.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.data.PaginationScrollListener;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.screeshot.Screenshot;
import com.mcpcustomer_post_new_ps_n.android.ui.adapter.CreativesAdapterWithOut;
import com.mcpcustomer_post_new_ps_n.android.ui.models.CreativesResponse;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreativesWithOut#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreativesWithOut extends Fragment implements  CreativesAdapterWithOut.CreativeClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCreativesWithOut() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCreatives.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCreativesWithOut newInstance(String param1, String param2) {
        FragmentCreativesWithOut fragment = new FragmentCreativesWithOut();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView creativesRVID;

    ProgressBar pb;
    AppCompatTextView noDataTVID;
    String cpPicStr,secondPicStr;
    private Screenshot screenshot;

    int start = 0;
    boolean isLoading;
    boolean isLastPage;
    public static boolean isClickable = true;
    LinearLayoutManager linearLayoutManager;
    CreativesAdapterWithOut adapter;
    ArrayList<CreativesResponse> creativesResponses = new ArrayList<>();

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_creatives,container,false);

        screenshot = new Screenshot(getActivity());
        cpPicStr = MySharedPreferences.getPreferences(getActivity(), "" + AppConstants.CP_PROFILE_PIC);



        creativesRVID = view.findViewById(R.id.creativesRVID);
        pb = view.findViewById(R.id.pb);
        noDataTVID = view.findViewById(R.id.noDataTVID);

        if (Utilities.isNetworkAvailable(Objects.requireNonNull(getActivity()))){
            loadCreatives();
        }else {
            Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadCreatives() {

        start = 0;
        isLoading=false;
        isLastPage=false;
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new CreativesAdapterWithOut(FragmentCreativesWithOut.this,creativesResponses, FragmentCreativesWithOut.this,cpPicStr);

        creativesRVID.setAdapter(adapter);
        creativesRVID.setLayoutManager(linearLayoutManager);
        creativesRVID.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.d("pagination","api called");
                isLastPage =false;
                isLoading = true;
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                Log.d("pagination","is last page");
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                Log.d("pagination","is loading");
                return isLoading;
            }
        });

        loadFirstPage();
    }

    private void loadFirstPage() {

        pb.setVisibility(View.VISIBLE);
        creativesRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.GONE);
        isClickable = false;

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<CreativesResponse>> call = apiInterface.getCreatives( start);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<CreativesResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CreativesResponse>> call, Response<ArrayList<CreativesResponse>> response) {

                pb.setVisibility(View.GONE);
                creativesRVID.setVisibility(View.VISIBLE);
                noDataTVID.setVisibility(View.GONE);
                //refreshFab.setVisibility(View.VISIBLE);
                isClickable = false;

                if (response.body() != null && response.code() == 200) {
                    creativesResponses.clear();
                    creativesResponses = response.body();
                    if (creativesResponses.size() > 0) {
                        isClickable = true;
                        startedValues();
                        adapter.addAll(creativesResponses);

                        if (creativesResponses.size() == 20) {
                            adapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    } else {
                        setErrorViews();
                    }
                } else {
                    setErrorViews();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CreativesResponse>> call, Throwable t) {
                setErrorViews();
            }
        });

    }

    private void loadNextPage() {

        Log.d("START_VALUE",""+start);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<CreativesResponse>> call = apiInterface.getCreatives( start);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<ArrayList<CreativesResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CreativesResponse>> call, Response<ArrayList<CreativesResponse>> response) {
                isClickable = true;
                if (response.body() != null && response.code() == 200) {
                    creativesResponses = response.body();
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    if (creativesResponses.size() > 0) {
                        adapter.addAll(creativesResponses);

                        if (creativesResponses.size() == 20) {
                            startedValues();
                            adapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                            adapter.removeLoadingFooter();
                        }
                    } else {
                        adapter.removeLoadingFooter();
                    }
                } else {
                    adapter.removeLoadingFooter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CreativesResponse>> call, Throwable t) {
                Log.d("ERROR : ", "" + t.getMessage());
                adapter.removeLoadingFooter();
            }
        });
    }


    private void setErrorViews() {
        isClickable = true;
        pb.setVisibility(View.GONE);
        creativesRVID.setVisibility(View.GONE);
        noDataTVID.setVisibility(View.VISIBLE);
    }

    private void startedValues() {
        start = start + 20;
    }

    @Override
    public void onCreativeItemClick(CreativesResponse response, View v, int pos, CreativesAdapterWithOut.CreativesVH holder) {
        switch (v.getId()) {
            case R.id.whatsAppShareIVID:

                try {
                    holder.creativeMainRLID.setDrawingCacheEnabled(true);
                    holder.creativeMainRLID.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap( holder.creativeMainRLID.getDrawingCache());
                    holder.creativeMainRLID.setDrawingCacheEnabled(false);
                    String url=response.getVideo_url();
                    screenshot.shareScreenShot(bitmap,url);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //screenshot.takeScreenshotFromView(holder.creativeMainRLID);


                break;

            case R.id.facebookShareIVID:

                try {
                    holder.creativeMainRLID.setDrawingCacheEnabled(true);
                    holder.creativeMainRLID.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap( holder.creativeMainRLID.getDrawingCache());
                    holder.creativeMainRLID.setDrawingCacheEnabled(false);
                    String url=response.getVideo_url();
                    shareToSocialMedia(bitmap,"2",url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.instagramShareIVID:

                try {
                    holder.creativeMainRLID.setDrawingCacheEnabled(true);
                    holder.creativeMainRLID.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap( holder.creativeMainRLID.getDrawingCache());
                    holder.creativeMainRLID.setDrawingCacheEnabled(false);
                    String url=response.getVideo_url();
                    shareToSocialMedia(bitmap,"3",url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.twitterShareIVID:

                try {
                    holder.creativeMainRLID.setDrawingCacheEnabled(true);
                    holder.creativeMainRLID.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap( holder.creativeMainRLID.getDrawingCache());
                    holder.creativeMainRLID.setDrawingCacheEnabled(false);
                    String url=response.getVideo_url();
                    shareToSocialMedia(bitmap,"4",url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.businessWhatsAppRLID:

                try {
                    holder.creativeMainRLID.setDrawingCacheEnabled(true);
                    holder.creativeMainRLID.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap( holder.creativeMainRLID.getDrawingCache());
                    holder.creativeMainRLID.setDrawingCacheEnabled(false);
                    String url=response.getVideo_url();
                    shareToSocialMedia(bitmap,"5",url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


        }
    }

    private void shareToSocialMedia(Bitmap bitmap, String socialMediaType,String url) {

        Log.e("Screen==>","" +"YES");

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), null);

            Log.e("screenshot==>",""+ path);

            Uri imageUri = Uri.parse(path);

            if (socialMediaType.equalsIgnoreCase("2")) {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("image/png");
                waIntent.setPackage("com.facebook.katana");
                waIntent.putExtra(Intent.EXTRA_TEXT, url);
                waIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(waIntent);
            } else if (socialMediaType.equalsIgnoreCase("3")){
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("image/png");
                waIntent.setPackage("com.instagram.android");
                waIntent.putExtra(Intent.EXTRA_TEXT, url);
                waIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(waIntent);
            }else if (socialMediaType.equalsIgnoreCase("4")){
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("image/png");
                waIntent.setPackage("com.twitter.android");
                waIntent.putExtra(Intent.EXTRA_TEXT, url);
                waIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(waIntent);
            }else if (socialMediaType.equalsIgnoreCase("5")){
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("image/png");
                waIntent.setPackage("com.whatsapp.w4b");
                waIntent.putExtra(Intent.EXTRA_TEXT, url);
                waIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(waIntent);
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (socialMediaType.equalsIgnoreCase("2")){
                Toast.makeText(getActivity(), "Facebook is not installed in your device", Toast.LENGTH_SHORT).show();
            }else if (socialMediaType.equalsIgnoreCase("3")){

                Toast.makeText(getActivity(), "Instagram is not installed in your device", Toast.LENGTH_SHORT).show();


            }else if (socialMediaType.equalsIgnoreCase("4")){

                Toast.makeText(getActivity(), "Twitter is not installed in your device", Toast.LENGTH_SHORT).show();


            }
        }


    }
}