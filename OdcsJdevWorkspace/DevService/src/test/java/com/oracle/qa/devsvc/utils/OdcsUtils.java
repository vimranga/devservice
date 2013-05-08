package com.oracle.qa.devsvc.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import java.util.logging.Logger;

import org.openqa.selenium.NoSuchElementException;

import sun.rmi.runtime.Log;


public class OdcsUtils {
    private static final Logger LOG =
        Logger.getLogger(OdcsUtils.class.getName());

    public OdcsUtils() {
        super();
    }
    
// return true if present and false if not present
    public static boolean verifyElementPresent(String locator,
                                              WebDriver se) {
        boolean result = false;
        try {
            result = se.findElement(By.xpath(locator)).isDisplayed();
            LOG.info("Element not found" + result);
        } catch (NoSuchElementException e) {
            LOG.info("Element absent");
            return false;
        }
        return result;
    }
    
    
    
    
}
