package platform.homepage;

import app.getxray.xray.testng.annotations.XrayTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import platform.InitTest;
import platform.api.models.user.UserProfileModel;
import platform.api.usermanagment.UserKeycloakApiUtil;
import platform.api.usermanagment.UserPlatformApiUtil;
import platform.pages.LoginPage;
import platform.sections.homepage.UserProfile;

public class UserProfileTest extends InitTest {

    @AfterMethod
    public void tearDown(){
        String userId = UserKeycloakApiUtil.searchForUsers(userName).get(0).getId();
        UserKeycloakApiUtil.setPassword(userId,password);
        UserProfileModel userProfileData = new UserProfileModel();
        userProfileData.setFirstName("Mohammed");
        userProfileData.setLastName("Gesreha");
        userProfileData.setEmail(userName);
        String token = UserPlatformApiUtil.login(userName, password);
        UserPlatformApiUtil.updateUserProfile(token, userProfileData);
    }

    @Test(description = "Test profile image update")
    @XrayTest(key = "")
    public void testProfileImageUpdate(){
        new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getUserProfileWidget()
                .open()
                .assertApplyButtonIsDisabled()
                .uploadProfileImage("src/test/resources/upload/CX_Logo.PNG")
                .assertApplyButtonIsEnabled()
                .applyChanges()
                .assertUserProfileIsClosed();
    }

    @Test(description = "Test password update")
    @XrayTest(key = "")
    public void testPasswordUpdate(){

        var newPassword = "Gesreha#7117";

        new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getUserProfileWidget()
                .open()
                .assertApplyButtonIsDisabled()
                .updateUserPassword(password, newPassword)
                .applyChanges()
                .assertUserProfileIsClosed();

        page.close();
        page = browser.newPage();

        new LoginPage(page)
                .navigate()
                .login(userName, newPassword)
                .isOpened();


    }

    @Test(description = "Test Name update")
    @XrayTest(key = "")
    public void testNameUpdate(){
        var firstName = "Automation";
        var lastName = "Test";

        UserProfile userProfileWidget = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getUserProfileWidget()
                .open();

        UserProfileModel userProfileData = userProfileWidget
                .assertApplyButtonIsDisabled()
                .updateUserName(firstName, lastName)
                .applyChanges()
                .assertUserProfileIsClosed()
                .open()
                .getUserProfileData();

        Assert.assertEquals(userProfileData.getFirstName(), firstName);
        Assert.assertEquals(userProfileData.getLastName(), lastName);
    }

    @Test(description = "Test cancel update functionality ")
    @XrayTest(key = "")
    public void testCancelUpdate(){
        var firstName = "Automation";
        var lastName = "Test";

        UserProfile userProfileWidget = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getUserProfileWidget()
                .open();

        UserProfileModel userProfileData = userProfileWidget
                .assertApplyButtonIsDisabled()
                .updateUserName(firstName, lastName)
                .cancelChanges()
                .assertUserProfileIsClosed()
                .open()
                .getUserProfileData();

        Assert.assertEquals(userProfileData.getFirstName(), "Mohammed");
        Assert.assertEquals(userProfileData.getLastName(), "Gesreha");
    }
}
