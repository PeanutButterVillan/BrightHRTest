package com.pw.m6;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.pw.AuthScriptBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class AuthenticationRefactored extends AuthScriptBase {

    @Test
    public void AddEmployees() {

        page.navigate("https://sandbox-app.brighthr.com/");

        // verify sign in
        page.click("data-testid=profileButton");

        String content = page.content();
        Assertions.assertTrue(content.contains("QA"));

        page.click("text=Employees");

        page.locator("button:text(\"Add employee\")").click();
        addEmployee();
        page.locator("button:text(\"Add another employee\")").click();
        addEmployee();

        Locator li = page.locator(".mr-6 text-link-link");
        int count = li.count();
        for (var i=1; i < count; i++) {
            Assertions.assertEquals("firstNameOne", li.nth(i).textContent());
        };

        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));


    }


    private void addEmployee()
    {

        page.fill("id=firstName", "firstNameOne");
        page.fill("id=lastName", "lastNameOne");
        page.fill("id=email", "email1@email1.com");
        page.fill("id=phoneNumber", "134567");

        page.locator("text=Select Date").click();
        page.locator("text=16").click();

        page.fill("id=jobTitle", "worker");
        page.click("text=Save new employee");
    }

}
