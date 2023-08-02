package platform.sections.homepage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import platform.api.models.user.UserProfileModel;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserProfile {
    private final Page page;
    private Locator profileMenuButton;
    private Locator menuItemProfile;
    private Locator profileImage;
    private Locator applyButton;
    private Locator cancelButton;
    private Locator newPassword;
    private Locator oldPassword;
    private Locator firstName;
    private Locator lastName;
    private Locator email;

    //TODO take care about the labels
    private String profileImageSelector="[name='profileImage']";
    private String emailSelector = "[name='profileImage']";

    public UserProfile(Page page) {
        this.page = page;
        profileMenuButton = page.getByTestId("profile-menu-button");
        menuItemProfile = page.getByTestId("menu-item-PROFILE");
        profileImage = page.locator(profileImageSelector);
        applyButton = page.getByTestId("apply");
        cancelButton = page.getByTestId("cancel");
        newPassword = page.getByTestId("newPassword");
        oldPassword = page.getByTestId("oldPassword");
        firstName = page.getByTestId("firstName");
        lastName = page.getByTestId("lastName");
        email = page.getByTestId("email");
     }

    public UserProfile open(){
        profileMenuButton.click();
        menuItemProfile.click();
        return this;
    }

    public UserProfile uploadProfileImage(String imagePath){
        profileImage.focus();
        profileImage.setInputFiles(Paths.get(imagePath));
        return this;
    }

    public UserProfile updateUserPassword(String oldPasswordText, String newPasswordText){
        newPassword.focus();
        newPassword.fill(newPasswordText);
        oldPassword.focus();
        oldPassword.fill(oldPasswordText);
        return this;
    }

    public UserProfile updateUserName(String firstNameText, String lastNameText){
        firstName.focus();
        firstName.fill(firstNameText);
        lastName.focus();
        lastName.fill(lastNameText);
        return this;
    }

    public UserProfile applyChanges(){
        Assert.assertTrue(applyButton.isEnabled());
        applyButton.click();
        return this;
    }

    public UserProfile cancelChanges(){
        cancelButton.click();
        return this;
    }

    public UserProfile assertApplyButtonIsEnabled(){
        applyButton.waitFor();
        Assert.assertTrue(applyButton.isEnabled());
        return this;
    }

    public UserProfile assertApplyButtonIsDisabled(){
        Assert.assertTrue(applyButton.isDisabled());
        return this;
    }

    public UserProfile assertUserProfileIsClosed(){
        assertThat(applyButton).not().isVisible();
        return this;
    }

    public UserProfileModel getUserProfileData(){
        UserProfileModel userProfileModel = new UserProfileModel();
        firstName.focus();
        userProfileModel.setFirstName(firstName.getAttribute("value"));
        lastName.focus();
        userProfileModel.setLastName(lastName.getAttribute("value"));
        email.focus();
        userProfileModel.setEmail(email.getAttribute("value"));
        return userProfileModel;
    }
}
