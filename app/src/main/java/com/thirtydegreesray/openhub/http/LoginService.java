package com.thirtydegreesray.openhub.http;

import androidx.annotation.NonNull;

import com.thirtydegreesray.openhub.mvp.model.BasicToken;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created on 2017/8/1.
 *
 * @author ThirtyDegreesRay
 */

public interface LoginService {

	@POST("authorizations")
	@Headers("Accept: application/json")
	Observable<Response<BasicToken>> authorizations(@NonNull @Body RequestBody requestBody);


//	@POST("authorizations")
//	@Headers("Accept: application/json")
//	Observable<Response<BasicToken>> authorizations(@NonNull @Body AuthRequestModel authRequestModel);
//
//	@POST("login/oauth/access_token")
//	@Headers("Accept: application/json")
//	Observable<Response<OauthToken>> getAccessToken(
//			@Query("client_id") String clientId,
//			@Query("client_secret") String clientSecret,
//			@Query("code") String code,
//			@Query("state") String state
//	);

}
