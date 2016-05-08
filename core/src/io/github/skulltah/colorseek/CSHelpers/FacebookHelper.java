package io.github.skulltah.colorseek.CSHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphRequest;
import de.tomgrill.gdxfacebook.core.JsonResult;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;
import io.github.skulltah.colorseek.CS.CSGame;

public class FacebookHelper {

    private static final String TAG = FacebookHelper.class.getSimpleName();
    private static final String NOT_LOGGED_IN = "You are not logged in.";
    private static final String FB_WALL_MESSAGE = "I've finally beat my highscore!\nI scored %s, bet ya you can't beat my score!";
    private static final String FB_WALL_LINK = "https://play.google.com/store/apps/details?id=io.github.skulltah.colorseek";
    private static final String FB_WALL_CAPTION = "Tap Chase for Android";
    private final CSGame game;
    private final GDXFacebook gdxFacebook;
    private Array<String> permissionsRead = new Array<String>();
    private Array<String> permissionsPublish = new Array<String>();

    private Preferences prefs;
    private int retryCount = 0;

    public FacebookHelper(GDXFacebook gdxFacebook, CSGame game) {
        this.game = game;
        this.gdxFacebook = gdxFacebook;

        /* create preferences to store autologin options */
        prefs = Gdx.app.getPreferences("gdx-facebook-app-data.txt");

        permissionsRead.add("email");
        permissionsRead.add("public_profile");
        permissionsRead.add("user_friends");

        permissionsPublish.add("publish_actions");

        /** perform auto login */
        if (prefs.getBoolean("autosignin", false)) {
            loginWithReadPermissions();
        }
    }

    private void loginWithReadPermissions() {
        gdxFacebook.signIn(SignInMode.READ, permissionsRead, new GDXFacebookCallback<SignInResult>() {
            @Override
            public void onSuccess(SignInResult result) {
                Gdx.app.debug(TAG, "SIGN IN (read permissions): User signed in successfully.");

                gainMoreUserInfo();
            }

            @Override
            public void onCancel() {
                Gdx.app.debug(TAG, "SIGN IN (read permissions): User canceled login process");
            }

            @Override
            public void onFail(Throwable t) {
                Gdx.app.error(TAG, "SIGN IN (read permissions): Technical error occured:");
                logout();
                t.printStackTrace();
            }

            @Override
            public void onError(GDXFacebookError error) {
                Gdx.app.error(TAG, "SIGN IN (read permissions): Error login: " + error.getErrorMessage());
                logout();
            }
        });
    }

    private void loginWithPublishPermissions(final Runnable onSuccess) {
        gdxFacebook.signIn(SignInMode.PUBLISH, permissionsPublish, new GDXFacebookCallback<SignInResult>() {
            @Override
            public void onSuccess(SignInResult result) {
                Gdx.app.debug(TAG, "SIGN IN (publish permissions): User logged in successfully.");

                gainMoreUserInfo();

                if (onSuccess != null) {
                    onSuccess.run();
                }
            }

            @Override
            public void onCancel() {
                Gdx.app.debug(TAG, "SIGN IN (publish permissions): User canceled login process");
            }

            @Override
            public void onFail(Throwable t) {
                Gdx.app.error(TAG, "SIGN IN (publish permissions): Technical error occured:");
                logout();
                t.printStackTrace();
            }

            @Override
            public void onError(GDXFacebookError error) {
                Gdx.app.error(TAG, "SIGN IN (publish permissions): Error login: " + error.getErrorMessage());
                logout();
            }
        });
    }

    private void gainMoreUserInfo() {
        GDXFacebookGraphRequest request = new GDXFacebookGraphRequest().setNode("me").useCurrentAccessToken();

        gdxFacebook.newGraphRequest(request, new GDXFacebookCallback<JsonResult>() {
            @Override
            public void onSuccess(JsonResult result) {
                JsonValue root = result.getJsonValue();

                String fbNickname = root.getString("name");
                String fbIdForThisApp = root.getString("id");

                Gdx.app.debug(TAG, "Graph Reqest: successful");
            }

            @Override
            public void onCancel() {
                logout();
                Gdx.app.debug(TAG, "Graph Reqest: Request cancelled. Reason unknown.");
            }

            @Override
            public void onFail(Throwable t) {
                Gdx.app.error(TAG, "Graph Reqest: Failed with exception.");
                logout();
                t.printStackTrace();
            }

            @Override
            public void onError(GDXFacebookError error) {
                Gdx.app.error(TAG, "Graph Reqest: Error. Something went wrong with the access token.");
                logout();
            }
        });
    }

    private void logout() {
        gdxFacebook.signOut();
    }

    public void postToUserWall(final int score) {
        GDXFacebookGraphRequest request = new GDXFacebookGraphRequest().setNode("me/feed").useCurrentAccessToken();
        request.setMethod(Net.HttpMethods.POST);
        request.putField("message", String.format(FB_WALL_MESSAGE, score));
        request.putField("link", FB_WALL_LINK);
        request.putField("caption", FB_WALL_CAPTION);
        gdxFacebook.newGraphRequest(request, new GDXFacebookCallback<JsonResult>() {
            @Override
            public void onFail(Throwable t) {
                Gdx.app.error(TAG, "Exception occured while trying to post to user wall.");
                t.printStackTrace();

                game.actionResolver.showToast("Couldn't share your score.");
            }

            @Override
            public void onCancel() {
                Gdx.app.debug(TAG, "Post to user wall has been cancelled.");
            }

            @Override
            public void onSuccess(JsonResult result) {
                Gdx.app.debug(TAG, "Posted to user wall successful.");
                Gdx.app.debug(TAG, "Response: " + result.getJsonValue().prettyPrint(JsonWriter.OutputType.json, 1));

                game.actionResolver.showToast("Score shared successfully!");
            }

            @Override
            public void onError(GDXFacebookError error) {
                Gdx.app.error(TAG, "An error occured while trying to post to user wall:" + error.getErrorMessage());

                if (retryCount > 3)
                    return;

                if (error.getErrorMessage().contains("2500")) {
                    // Not logged in

                    if (retryCount > 0)
                        game.actionResolver.showToast("Sign in to share your score.");

                    loginWithPublishPermissions(new Runnable() {
                        public void run() {
                            // Logged in successfully!

                            postToUserWall(score);
                        }
                    });
                }

                retryCount++;
            }
        });
    }
}
