package com.thirtydegreesray.openhub.mvp.presenter;

import androidx.annotation.NonNull;
import android.util.Log;

import com.thirtydegreesray.openhub.AppData;
import com.thirtydegreesray.openhub.dao.AuthUser;
import com.thirtydegreesray.openhub.dao.AuthUserDao;
import com.thirtydegreesray.openhub.dao.DaoSession;
import com.thirtydegreesray.openhub.mvp.contract.ISplashContract;
import com.thirtydegreesray.openhub.mvp.model.User;
import com.thirtydegreesray.openhub.mvp.presenter.base.BasePresenter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created on 2017/7/12.
 *
 * @author ThirtyDegreesRay
 */

public class SplashPresenter extends BasePresenter<ISplashContract.View>
		implements ISplashContract.Presenter {

	private final String TAG = "SplashPresenter";

	private AuthUser authUser;
	private boolean isMainPageShowwed = false;

	@Inject
	public SplashPresenter(DaoSession daoSession) {
		super(daoSession);
	}

	@Override
	public void getUser() {
		AuthUserDao authUserDao = daoSession.getAuthUserDao();

		List<AuthUser> users = authUserDao.queryBuilder()
				.where(AuthUserDao.Properties.Selected.eq(true))
				.limit(1)
				.list();

		AuthUser selectedUser = users != null && users.size() > 0 ? users.get(0) : null;

		//if none selected, choose first account
		if (selectedUser == null) {
			List<AuthUser> firstAccount = authUserDao.queryBuilder()
					.limit(1)
					.list();
			selectedUser = firstAccount != null && firstAccount.size() > 0 ? firstAccount.get(0) : null;
		}

		if (selectedUser != null && isExpired(selectedUser)) {
			authUserDao.delete(selectedUser);
			selectedUser = null;
		}

		if (selectedUser != null) {
			AppData.INSTANCE.setAuthUser(selectedUser);
			getUserInfo(selectedUser.getAccessToken());
			Log.i("fafadfaf", "1");
		} else {
			mView.showLoginPage();
			Log.i("fafadfaf", "2");
		}

	}

	@Override
	public void saveAccessToken(String accessToken, String scope, int expireIn) {
		AuthUser authUser = new AuthUser();
		authUser.setSelected(true);
		authUser.setScope(scope);
		authUser.setExpireIn(expireIn);
		authUser.setAuthTime(new Date());
		authUser.setAccessToken(accessToken);
		daoSession.getAuthUserDao().insert(authUser);
		this.authUser = authUser;
	}

	private void getUserInfo(final String accessToken) {
		Log.i("fafadfaf", "-->");


//		HttpObserver<User> httpObserver = new HttpObserver<User>() {
//			@Override
//			public void onError(@NonNull Throwable error) {
//				daoSession.getAuthUserDao().delete(AppData.INSTANCE.getAuthUser());
//				AppData.INSTANCE.setAuthUser(null);
//				mView.showErrorToast(getErrorTip(error));
//				mView.showLoginPage();
//			}
//
//			@Override
//			public void onSuccess(@NonNull HttpResponse<User> response) {
//				AppData.INSTANCE.setLoggedUser(response.body());
//				if (authUser != null) {
//					authUser.setLoginId(response.body().getLogin());
//					daoSession.getAuthUserDao().update(authUser);
//				}
//				if (!isMainPageShowwed) {
//					isMainPageShowwed = true;
//					mView.showMainPage();
//				}
//			}
//		};
//
//		generalRxHttpExecute(new IObservableCreator<User>() {
//			@Override
//			public Observable<Response<User>> createObservable(boolean forceNetWork) {
//				return getUserService().getPersonInfo(forceNetWork);
//			}
//		}, httpObserver, true);

		Disposable disposable = getUserService().getPersonInfo(true).observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(new Consumer<Response<User>>() {
					@Override
					public void accept(Response<User> userResponse) throws Exception {
						Log.i("fafadfaf", userResponse.toString());
					}
				});

	}

	private boolean isExpired(AuthUser selectedUser) {
		return selectedUser.getAuthTime().getTime() + selectedUser.getExpireIn() * 1000
				< System.currentTimeMillis();
	}

}
