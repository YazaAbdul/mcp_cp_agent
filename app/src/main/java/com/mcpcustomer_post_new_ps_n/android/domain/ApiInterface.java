package com.mcpcustomer_post_new_ps_n.android.domain;

import com.mcpcustomer_post_new_ps_n.android.ui.models.AddCustomerResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AddSaleHistoryResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AgentnewpasswordResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AllStatusResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AvailabilityResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.AvailableDetailsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BankerCheckListDTO;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BhkResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BonanzaResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ClubDetailsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.CreativesResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashBordPerformanceResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DesignationsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamMainResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectTeamResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DirectteamcountRespon;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DocumentsObj;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueCustomersResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DueResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.CustomerLoginResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.EligibleCommissionResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.FloorPlansPOJO;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ImageResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.LoginResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MaterialResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MelaPlansResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MelaResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.NewDashboardResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.NumberResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.PersonalDetailsOBJ;
import com.mcpcustomer_post_new_ps_n.android.ui.models.PlotDetailsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectDashBoardResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectMenuResponce;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.RegistrationResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.RequirementResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ScrollerResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SearchResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SearchSuggestions;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ShareResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SiteInchargeResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SiteVisitResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SubmitResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.TotalmilesAmountOBJ;
import com.mcpcustomer_post_new_ps_n.android.ui.models.YoutubeVideoResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewRegistrationDelayChargesResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewRegistrationPaymentDetailsResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewTransactionPaidResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models._NewTrasactionDiscountResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by venkei on 20-Nov-19.
 */
public interface ApiInterface {

    //customer apis

    @GET("mobileapp/siteincharge")
    Call<ArrayList<SiteInchargeResponse> > getSiteInCharges();

    @Multipart
    @POST("mobileapp/insertsitevisitor")
    Call<StatusResponseNew> insertSiteVisitor(@Query("customer_name") String customer_name,
                                              @Query("mobile_number") String mobile_number,
                                              @Query("agent_id") String agent_id,
                                              @Query("project_id") String project_id,
                                              @Part MultipartBody.Part upload_pic);

    @GET("mobileapp/getsitevisitor")
    Call<ArrayList<SiteVisitResponse> > getSiteVisits(@Query("agent_id") String agentid,
                                                      @Query("start") int start);

    @GET("mobileapp/insertsitevisitcust")
    Call<ArrayList<AddCustomerResponse> > getAddCustomer(@Query("agent_id") String agentid,
                                                         @Query("customer_name") String nameStr,
                                                         @Query("mobile_no") String mobilenumberStr,
                                                         @Query("date_time") String date_time,
                                                         @Query("no_of_pass") String no_of_pass,
                                                         @Query("projects") String projects,
                                                         @Query("address") String address,
                                                         @Query("user_id") String user_id,
                                                         @Query("iba_name") String iba_name,
                                                         @Query("iba_no") String iba_no,
                                                         @Query("time") String time);

    @GET("mobileapp/ReferCustomer")
    Call<ArrayList<StatusResponseNew> > getReferCustomer(@Query("customer_name") String customer_name,
                                                         @Query("mobile_number") String mobile_number,
                                                         @Query("email_id") String email_id,
                                                         @Query("project_id") String project_id,
                                                         @Query("customer_id") String customer_id,
                                                         @Query("sold_by") String sold_by);

    @GET("mobileapp/CommissionWithdrawRequest")
    Call<ArrayList<StatusResponseNew> > getWithDrawRequest(@Query("agent_id") String agentid,
                                                           @Query("amount") String amount,
                                                           @Query("notes") String notes);

    @GET("mobileapp/RequestMaterial")
    Call<ArrayList<StatusResponseNew> > getRequestMaterial (@Query("material_id") String material_id,
                                                            @Query("qty") String qty,
                                                            @Query("price_per_unit") String price_per_unit,
                                                            @Query("agent_id") String agent_id);

    @GET("mobileapp/saleshistorydue")
    Call<ArrayList<DueCustomersResponse>> getDueCustomers (@Query("agent_id") String agent_id,
                                                           @Query("type") String type);

    @GET("mobileapp/scroller")
    Call<ArrayList<ScrollerResponse>> getScrollerText ();

    @GET("mobileapp/creativesnew")
    Call<ArrayList<CreativesResponse>> getCreatives (@Query("start") int start);

    @GET("mobileapp/melaplan")
    Call<ArrayList<StatusResponseNew> > getMelaPlan (@Query("mela_id") String mela_id,
                                                            @Query("agent_id") String agent_id,
                                                            @Query("customer_count") String customer_count,
                                                            @Query("associate_count") String associate_count);

    @GET("mobileapp/getallmelaupdate")
    Call<StatusResponseNew> getAllMelaPlanUpdate (@Query("s_no") String s_no,
                                                     @Query("agent_id") String agent_id,
                                                     @Query("customer_count") String customer_count,
                                                     @Query("associate_count") String associate_count);

    @GET("mobileapp/getallmelaplan")
    Call<ArrayList<MelaPlansResponse> > getAllMela (@Query("mela_id") String mela_id,
                                                     @Query("agent_id") String agent_id);

    @GET("mobileapp/clubs")
    Call<ArrayList<ClubDetailsResponse> > getClubDetails (@Query("agent_id") String agent_id);

    @GET("mobileapp/printassperfdirectnew")
    Call<DirectTeamMainResponse> getDirectTeam (@Query("agent_id") String agent_id);

    @GET("mobileapp/directteamcount")
    Call<ArrayList<DirectteamcountRespon>> Getdirectteamcount (@Query("agent_id") String agent_id);




    @GET("mobileapp/printassperfnew")
    Call<DirectTeamMainResponse> getTotalTeam (@Query("agent_id") String agent_id);


    @GET("mobileapp/directagentteam")
    Call<DirectTeamMainResponse> getdirectagentteam (@Query("agent_id") String agent_id);



    @GET("mobileapp/getallmeladelete")
    Call<StatusResponseNew> getMelaDelete (@Query("s_no") String s_no);

    @GET("mobileapp/getallmela")
    Call<ArrayList<MelaResponse>> getMela(@Query("agent_id") String agent_id);

    @GET("mobileapp/showbonanza")
    Call<ArrayList<BonanzaResponse>> showBonanza();


    @GET("mobileapp/marketing_material")
    Call<ArrayList<MaterialResponse>> getMaterials();

    @GET("mobileapp/getalldescgnation")
    Call<ArrayList<DesignationsResponse>> getDesignations(@Query("agent_id")String agent_id);

    @GET("mobileapp/eligiblecommission")
    Call<EligibleCommissionResponse> getEligibleCommission(@Query("agent_id")String agent_id);


    @GET("mobileapp/requirement")
    Call<ArrayList<RequirementResponse>>getRequirementResponse();

    @GET("mobileapp/requirement")
    Call<ArrayList<ProjectsResponse>>getProjects();

    @GET("mobileapp/saleshistory")
    Call<ArrayList<AddSaleHistoryResponse>>getsalehistory(@Query("agent_id")String agent_id);

    @GET("customerapp/custlogin")
    Call<CustomerLoginResponse> customerLogin(@Query("mobileoremail") String customer_id,
                                         @Query("password") String password);

    @GET("customerapp/updatedetails")
    Call<SubmitResponse> submitCustomerDetailsAPi(@Query("customer_id") String customer_id,
                                                  @Query("application_id") String application_id,
                                                  @Query("father_name") String father_name,
                                                  @Query("spouse_name") String spouse_name,
                                                  @Query("nominee_name") String nominee_name,
                                                  @Query("alternative_contact_number") String alternative_contact_number,
                                                  @Query("relation_with_applicant") String relation_with_applicant,
                                                  @Query("address_for_communication") String address_for_communication,
                                                  @Query("permanent_address") String permanent_address,
                                                  @Query("project_id") String project_id,
                                                  @Query("plot_number") String plot_number);

    @GET("customerapp/personaldetails")
    Call<PersonalDetailsOBJ> personalDetailsApi(@Query("customer_id") String customer_id);

    @Multipart
    @POST("customerapp/uploadimages")
    Call<DocumentsObj> uploadDocuments(@Part MultipartBody.Part image,
                                       @Query("application_id") String application_id,
                                       @Query("customer_id") String customer_id,
                                       @Query("file_type") String file_type);

    @Multipart
    @POST("customerapp/uploadpancard")
    Call<DocumentsObj> uploadpancard(@Part MultipartBody.Part image,
                                     @Query("customer_id") String customer_id);
    @Multipart
    @POST("mobileapp/updateagentprofile")
    Call<ArrayList<StatusResponseNew>> updateAgentProfile(@Part MultipartBody.Part upload_pic,
                                               @Query("agent_id") String agent_id,
                                               @Query("agent_name") String agent_name,
                                               @Query("email") String email,
                                               @Query("contact_number") String contact_number);

    @Multipart
    @POST("customerapp/uploadaddressproof")
    Call<DocumentsObj> uploadaddressproof(@Part MultipartBody.Part image,
                                          @Query("customer_id") String customer_id);



    @Multipart
    @POST("customerapp/checklistupload")
    Call<DocumentsObj> uploadBankDocuments(@Part MultipartBody.Part image,
                                       @Query("checklist_id") String checklist_id,
                                       @Query("customer_id") String customer_id);



    @GET("customerapp/transactionhistoryscheduled")
    Call<ArrayList<DueResponse>>scheduledApi(@Query("customer_id")String customer_id);

/*
    @GET("customerapp/transactionhistoryscheduled")
    Call<ArrayList<>>scheduledApi(@Query("customer_id")String customer_id);
*/

    @GET("customerapp/totalmilestoneamt")
    Call<TotalmilesAmountOBJ> totalmilestoneamtAPi(@Query("customer_id") String customer_id);
    @GET("customerapp/transactiondiscountdetails")
    Call<ArrayList<_NewTrasactionDiscountResponse>>discountGivenApi(@Query("customer_id")String customer_id);


    @GET("customerapp/transactionhistorypaid")
    Call<ArrayList<_NewTransactionPaidResponse>>paidApi(@Query("customer_id")String customer_id);


    @GET("customerapp/registrationdelaycharges")
    Call<ArrayList<_NewRegistrationDelayChargesResponse>>registrationDelayApi(@Query("customer_id")String customer_id);

    @GET("customerapp/registrationpaymentdetails")
    Call<ArrayList<_NewRegistrationPaymentDetailsResponse>>registrationPaidApi(@Query("customer_id")String customer_id);


    @GET("customerapp/bankerchecklist")
    Call<ArrayList<BankerCheckListDTO>>bankerCheckListApi(@Query("customer_id")String customer_id);


    //sales


    @GET("customerapp/newregister")
    Call<RegistrationResponse>registerApi(@Query("fullname") String fullname,
                                                      @Query("email") String email,
                                                      @Query("mobile") String mobile,
                                                      @Query("password") String password,
                                                      @Query("gcm_id") String gcm_id);


    @GET("customerapp/forgotpassword")
    Call<ArrayList<StatusResponse>> forgotPasswordApi(@Query("email") String email);

    @GET("customerapp/changepassword")
    Call<ArrayList<StatusResponse>> changePasswordApi(@Query("email") String email,
                                                      @Query("password") String password);


    @GET("customerapp/phonecallrecords")
    Call<ArrayList<StatusResponse>> phoneCallRecord(@Query("fullname") String fullname,
                                                    @Query("email") String email,
                                                    @Query("mobile") String mobile,
                                                    @Query("user_id") String user_id);


    //new
    //availability
    @GET("customerapp/allprojectplotstypes")
    Call<ArrayList<BhkResponse>> getBhkNames(@Query("project_id") String project_id);

    @GET("customerapp/allprojectslist")
    Call<ArrayList<AvailabilityResponse>> getProjectNames();

    @GET("customerapp/customerappcall")
    Call<NumberResponse> getCustomerappcall();

    @GET("customerapp/customerappshare")
    Call<ShareResponse> getCustomerappshare();
    @GET("customerapp/allprojectplots")
    Call<ArrayList<AvailableDetailsResponse>> getPlotNames(@Query("project_id") String project_id,
                                                           @Query("bhk_type") String bhk_type);

    @GET("customerapp/allprojectplots")
    Call<ArrayList<AvailableDetailsResponse>> getPlotNames(@Query("project_id") String project_id);


    @GET("customerapp/allplotsdetails")
    Call<ArrayList<PlotDetailsResponse>> getPlotDetails(@Query("project_id") String project_id, @Query("plot_id") String plot_id);


    @GET("customerapp/autofill")
    Call<ArrayList<SearchSuggestions>> searchApi(@Query("keyword") String keyword);

   @GET("customerapp/customerfiles")
   Call<ArrayList<FloorPlansPOJO>>getFloorAPI(@Query("customer_id")String customer_id,
                                              @Query("payment_id")String payment_id,
                                              @Query("type")String type);



    //cp
    @GET("mobileapp/agentnewpassword")
    Call<ArrayList<AgentnewpasswordResponse>> getChangePasswordResponse(@Query("agent_id") String agent_id,
                                                                        @Query("agent_password") String agent_password);


    @GET("mobileapp/cplogin")
    Call<ArrayList<LoginResponse>>loginApi(@Query("email")String email,
                                           @Query("password")String password);

    @GET("mobileapp/activitytypesplanned")
    Call<ArrayList<NewDashboardResponse>> getOverAllMAinCount(@Query("cp_id") String user_id);

    @GET("mobileapp/activitytypescompleted")
    Call<ArrayList<NewDashboardResponse>> getTodayMAinCount(@Query("cp_id") String user_id);

    @GET("mobileapp/cpdashboard")
    Call<ArrayList<DashBordPerformanceResponse>>getUserDashBoardCount(@Query("cp_id") String user_id);


    @GET("mobileapp/projectsformobileapp")
    Call<ArrayList<ProjectsResponse>> projectsApi();

    @GET("mobileapp/createcplead")
    Call<ArrayList<AllStatusResponse>> createLeadApi(@Query("fullname")String fullname,
                                                     @Query("mobile_number")String mobile_number,
                                                     @Query("email")String email,
                                                     @Query("notes")String notes,
                                                     @Query("cp_id")String cp_id,
                                                     @Query("project_id")String project_id);

    @GET("mobileapp/cpsearch")
    Call<ArrayList<SearchResponse>>searchApi(@Query("mobileorname")String mobileorname,
                                             @Query("cp_id")String cp_id,
                                             @Query("start")String start);

    @GET("mobileapp/agentsearch")
    Call<ArrayList<DirectTeamResponse>>searchAgent(@Query("mobileorname")String mobileorname,
                                                   @Query("agent_id")String agent_id);

    @GET("customerapp/menucontentnew")
    Call<ArrayList<ProjectDashBoardResponce>> projectDashboardApi(@Query("menu_id") String menu_id);

    @GET("customerapp/menunext")
    Call<ArrayList<ProjectMenuResponce>> projectMenuApi (@Query("project_id") String project_id,
                                                         @Query("start") int start);

    @GET("customerapp/customerfiles")
    Call<ArrayList<ImageResponse>> imagesApi(
            @Query("customer_id")String customer_id,
            @Query("payment_id")String payment_id,
            @Query("type")String type);

    @GET("customerapp/customerfiles")
    Call<ArrayList<YoutubeVideoResponse>> videosApi(
            @Query("customer_id")String customer_id,
            @Query("payment_id")String payment_id,
            @Query("type")String type);

}
