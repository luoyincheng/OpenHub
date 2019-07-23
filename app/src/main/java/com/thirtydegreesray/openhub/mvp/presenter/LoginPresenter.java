package com.thirtydegreesray.openhub.mvp.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thirtydegreesray.openhub.dao.DaoSession;
import com.thirtydegreesray.openhub.http.model.AuthRequestModel;
import com.thirtydegreesray.openhub.mvp.contract.ILoginContract;
import com.thirtydegreesray.openhub.mvp.model.BasicToken;
import com.thirtydegreesray.openhub.mvp.presenter.base.BasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import retrofit2.Response;

/**
 * Created on 2017/7/12.
 *
 * @author ThirtyDegreesRay
 */

public class LoginPresenter extends BasePresenter<ILoginContract.View>
		implements ILoginContract.Presenter {

	@Inject
	public LoginPresenter(DaoSession daoSession) {
		super(daoSession);
	}

	@Override
	public void getToken(String code, String state) {
//		Observable<Response<OauthToken>> observable =
//				getLoginService().getAccessToken(AppConfig.OPENHUB_CLIENT_ID,
//						AppConfig.OPENHUB_CLIENT_SECRET, code, state);
//
//		HttpSubscriber<OauthToken> subscriber =
//				new HttpSubscriber<>(
//						new HttpObserver<OauthToken>() {
//							@Override
//							public void onError(@NonNull Throwable error) {
//								mView.dismissProgressDialog();
//								mView.showErrorToast(getErrorTip(error));
//							}
//
//							@Override
//							public void onSuccess(@NonNull HttpResponse<OauthToken> response) {
//								OauthToken token = response.body();
//								if (token != null) {
//									mView.onGetTokenSuccess(BasicToken.generateFromOauthToken(token));
//								} else {
//									mView.onGetTokenError(response.getOriResponse().message());
//								}
//							}
//						}
//				);
//		generalRxHttpExecute(observable, subscriber);
//		mView.showProgressDialog(getLoadTip());
	}

	@NonNull
	@Override
	public String getOAuth2Url() {
//		String randomState = UUID.randomUUID().toString();
//		return AppConfig.OAUTH2_URL +
//				"?client_id=" + AppConfig.OPENHUB_CLIENT_ID +
//				"&scope=" + AppConfig.OAUTH2_SCOPE +
//				"&state=" + randomState;
		return "";
	}

	@Override
	public void basicLogin(String userName, String password) {
		AuthRequestModel authRequestModel = AuthRequestModel.generate();
		JSONArray jsonArray = new JSONArray();
		jsonArray.put("user");
		jsonArray.put("repo");
		jsonArray.put("gist");
		jsonArray.put("notifications");
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("scopes", jsonArray);
			jsonObject.put("note", "com.thirtydegreesray.openhub");
			jsonObject.put("noteUrl", "https://github.com/ThirtyDegreesRay/OpenHub/CallBack");
			jsonObject.put("client_id", "8f7213694e115df205fb");
			jsonObject.put("clientSecret", "82c57672382db5c7b528d79e283c398ad02e3c3f");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String token = Credentials.basic(userName, password);
		Log.i("loginway", "token:" + token);
		Observable<Response<BasicToken>> observable =
				getLoginService(token).authorizations(jsonObject);
//		HttpSubscriber<BasicToken> subscriber =
//				new HttpSubscriber<>(
//						new HttpObserver<BasicToken>() {
//							@Override
//							public void onError(@NonNull Throwable error) {
////                                mView.dismissProgressDialog();
//								mView.onGetTokenError(getErrorTip(error));
//							}
//
//							@Override
//							public void onSuccess(@NonNull HttpResponse<BasicToken> response) {
//
//
//							}
//						}
//				);
//		generalRxHttpExecute(observable, subscriber);

		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Response<BasicToken>>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Response<BasicToken> basicTokenResponse) {
						BasicToken token = basicTokenResponse.body();
						Log.i("loginway", basicTokenResponse.toString());
						if (token != null) {
							Log.i("loginway", token.toString());
							mView.onGetTokenSuccess(token);
						} else {
							mView.onGetTokenError(basicTokenResponse.message());
						}
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
//        mView.showProgressDialog(getLoadTip());
	}

	@Override
	public void handleOauth(Intent intent) {
//		Uri uri = intent.getData();
//		if (uri != null) {
//			String code = uri.getQueryParameter("code");
//			String state = uri.getQueryParameter("state");
//			getToken(code, state);
//		}
	}

	@Override
	public void getUserInfo(final BasicToken basicToken) {
//		HttpSubscriber<User> subscriber = new HttpSubscriber<>(
//				new HttpObserver<User>() {
//					@Override
//					public void onError(Throwable error) {
//						mView.dismissProgressDialog();
//						mView.showErrorToast(getErrorTip(error));
//					}
//
//					@Override
//					public void onSuccess(HttpResponse<User> response) {
////                        mView.dismissProgressDialog();
//						saveAuthUser(basicToken, response.body());
//						mView.onLoginComplete();
//					}
//				}
//		);
//		Observable<Response<User>> observable = getUserService(basicToken.getToken()).
//				getPersonInfo(true);
//		generalRxHttpExecute(observable, subscriber);
//		mView.showProgressDialog(getLoadTip());

	}

//	private void saveAuthUser(BasicToken basicToken, User userInfo) {
//		String updateSql = "UPDATE " + daoSession.getAuthUserDao().getTablename()
//				+ " SET " + AuthUserDao.Properties.Selected.columnName + " = 0";
//		daoSession.getAuthUserDao().getDatabase().execSQL(updateSql);
//
//		String deleteExistsSql = "DELETE FROM " + daoSession.getAuthUserDao().getTablename()
//				+ " WHERE " + AuthUserDao.Properties.LoginId.columnName
//				+ " = '" + userInfo.getLogin() + "'";
//		daoSession.getAuthUserDao().getDatabase().execSQL(deleteExistsSql);
//
//		AuthUser authUser = new AuthUser();
//		String scope = StringUtils.listToString(basicToken.getScopes(), ",");
//		Date date = new Date();
//		authUser.setAccessToken(basicToken.getToken());
//		authUser.setScope(scope);
//		authUser.setAuthTime(date);
//		authUser.setExpireIn(360 * 24 * 60 * 60);
//		authUser.setSelected(true);
//		authUser.setLoginId(userInfo.getLogin());
//		authUser.setName(userInfo.getName());
//		authUser.setAvatar(userInfo.getAvatarUrl());
//		daoSession.getAuthUserDao().insert(authUser);
//
//		AppData.INSTANCE.setAuthUser(authUser);
//		AppData.INSTANCE.setLoggedUser(userInfo);
//	}
}
