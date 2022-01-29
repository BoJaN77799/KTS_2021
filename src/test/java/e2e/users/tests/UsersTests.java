package e2e.users.tests;

import e2e.commonPages.AdminPage;
import e2e.commonPages.LoginPage;
import e2e.users.pages.CreateUserPage;
import e2e.users.pages.UserViewPage;
import e2e.users.pages.UsersPage;
import e2e.utils.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTests {
    private static WebDriver driver;

    private static UsersPage usersPage;

    private static UserViewPage userViewPage;

    private static CreateUserPage userCreatePage;

    private static LoginPage loginPage;

    private static AdminPage adminPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to(Constants.BASE_URL);

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        usersPage = PageFactory.initElements(driver, UsersPage.class);
        adminPage = PageFactory.initElements(driver, AdminPage.class);
        userViewPage = PageFactory.initElements(driver, UserViewPage.class);
        userCreatePage = PageFactory.initElements(driver, CreateUserPage.class);
    }

    @Test
    public void usersTests(){
        // login as admin
        loginPage.setEmailInput(Constants.ADMIN_EMAIL);
        loginPage.setPasswordInput(Constants.ADMIN_PASSWORD);
        loginPage.loginButtonClick();

        //go to users page
        adminPage.usersLinkClick();
        assertTrue(usersPage.getUrl());

        //profile view
        adminPage.profileLinkClick();
        userViewPage.addTextToFNField("Filipko");
        userViewPage.addTextToLNField("Markovicko");
        userViewPage.addTextToAddressField("Plava, Dunav");
        userViewPage.addTextToTelephoneField("1891991919");
        userViewPage.clickSave();

        adminPage.profileLinkClick();
        assertTrue(userViewPage.checkTextinFN("Filipko"));
        assertTrue(userViewPage.checkTextinLN("Markovicko"));
        assertTrue(userViewPage.checkTextinAddress("Plava, Dunav"));
        assertTrue(userViewPage.checkTextinTelephone("1891991919"));
        userViewPage.clickEsc();

        //change pw check
        adminPage.profileLinkClick();
        userViewPage.clickChangePassword();
        userViewPage.addTextToOldPw("admin");
        userViewPage.addTextToNewPw("admin123123");
        userViewPage.clickSaveChangePass();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userViewPage.clickEsc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adminPage.logOutLinkClick();
        loginPage.setEmailInput(Constants.ADMIN_EMAIL);
        loginPage.setPasswordInput("admin123123");
        loginPage.loginButtonClick();

        adminPage.usersLinkClick();
        assertTrue(usersPage.getUrl());

        //user update
        usersPage.clickOnUpdate("2");
        userViewPage.addTextToFNField("Petarko");
        userViewPage.addTextToLNField("Djuricko");
        userViewPage.addTextToAddressField("Beograd");
        userViewPage.addTextToTelephoneField("213123123");

        userViewPage.clickSave();
        assertTrue(usersPage.checkOnUpdateOfUser("2", "Petarko", "Djuricko", "213123123"));

        //user delete - zakomentarisan jer ne moze dva puta za redom
        usersPage.clickOnDelete();
        assertTrue(usersPage.checkIfDeleted("3"));

        //sortiranje
        usersPage.clickSortId();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(usersPage.checkSortById(true));
        assertTrue(usersPage.checkIdsInOrder(new ArrayList<>(List.of("1", "2", "3"))));


        usersPage.clickSortId();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(usersPage.checkSortById(false));
        assertTrue(usersPage.checkIdsInOrder(new ArrayList<>(List.of("12", "11", "10"))));


        usersPage.clickSortFirstName();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(usersPage.checkSortByFirstName(true));
        assertTrue(usersPage.checkIdsInOrder(new ArrayList<>(List.of("5", "7", "4"))));


        usersPage.clickSortFirstName();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(usersPage.checkSortByFirstName(false));
        assertTrue(usersPage.checkIdsInOrder(new ArrayList<>(List.of("6", "12", "11"))));


        usersPage.clickSortLastName();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(usersPage.checkSortByLastName(true));
        assertTrue(usersPage.checkIdsInOrder(new ArrayList<>(List.of("7", "5", "3"))));


        usersPage.clickSortLastName();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(usersPage.checkSortByLastName(false));
        assertTrue(usersPage.checkIdsInOrder(new ArrayList<>(List.of("6", "4", "8"))));
        usersPage.clickSortLastName();

        //check num of pages
        assertTrue(usersPage.checkNumberOfPages(3));

        //check shown user ids
        List<String> listIds = new ArrayList<>(List.of("1", "2", "3"));
        List<String> listNames;
        assertTrue(usersPage.checkIdsInOrder(listIds));

        //click next page btn
        usersPage.paginationClick("next");
        listIds = new ArrayList<>(List.of("4", "5", "6"));
        assertTrue(usersPage.checkIdsInOrder(listIds));

        usersPage.paginationClick("next");
        listIds = new ArrayList<>(List.of("7", "8", "9"));
        assertTrue(usersPage.checkIdsInOrder(listIds));

        //return to the first
        usersPage.paginationClick("1");

        //try out search field
        usersPage.addTextToSearchField("pe");
        listIds = new ArrayList<>(List.of("2", "10", "11"));
        listNames = new ArrayList<>(List.of("Petarko", "Pele", "Pex"));
        assertTrue(usersPage.checkNamesInOrder(listNames, listIds));

        usersPage.addTextToSearchField("ca");
        listIds = new ArrayList<>(List.of("3", "4", "8"));
        listNames = new ArrayList<>(List.of("Bjelica", "Tica", "Slatkica"));
        assertTrue(usersPage.checkLastNamesInOrder(listNames, listIds));

        usersPage.addTextToSearchField("waiter_marko@maildrop.cc");
        listIds = new ArrayList<>(List.of("3"));
        assertTrue(usersPage.checkIdsInOrder(listIds));

        usersPage.addTextToSearchField("pel");
        usersPage.clickOnSelectUserType(2); //manager
        listIds = new ArrayList<>(List.of("10"));
        listNames = new ArrayList<>(List.of("MANAGER"));
        assertTrue(usersPage.checkUserTypeInOrder(listNames, listIds));

        usersPage.addTextToSearchField("v");
        usersPage.clickOnSelectUserType(4); //cook
        listIds = new ArrayList<>(List.of("6", "7"));
        listNames = new ArrayList<>(List.of("COOK", "COOK"));
        assertTrue(usersPage.checkUserTypeInOrder(listNames, listIds));

        //RESETUJEMO
        usersPage.clickOnSelectUserType(0); //any
        usersPage.addTextToSearchField("m");
        listIds = new ArrayList<>(List.of("1", "2", "3"));
        assertTrue(usersPage.checkIdsInOrder(listIds));

        //user create
        usersPage.clickOnCreate();
        userCreatePage.addTextToFNField("Lola");
        userCreatePage.addTextToLNField("Lolic");
        userCreatePage.addTextToEmailField("lola@maildrop.cc");
        userCreatePage.addTextToTelephoneField("928320198");
        userCreatePage.addTextToAddressField("Zelena, Temerin");
        userCreatePage.clickOnSelectGender(8);
        userCreatePage.clickOnSelectUserType(11);
        userCreatePage.clickCreate();

        usersPage.addTextToSearchField("lola");
        assertTrue(usersPage.checkOnCreateOfUser("Lola", "Lolic", "928320198",
                "lola@maildrop.cc", "MANAGER"));

    }
}
