package com.automation.listeners;

import com.automation.config.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureTestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(AllureTestListener.class);

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info("========== INICIANDO SUITE: {} ==========", iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("========== FINALIZANDO SUITE: {} ==========", iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("Iniciando prueba: {}", getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info("Test EXITOSO: {}", getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.error("Test FALLIDO: {}", getTestMethodName(iTestResult));

        // Tomar screenshot y adjuntarlo a Allure
        try {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
            logger.info("Screenshot tomado para el test fallido: {}", getTestMethodName(iTestResult));
        } catch (Exception e) {
            logger.error("Error al tomar screenshot en el fallo: " + e.getMessage());
        }

        // Adjuntar el mensaje de error
        if (iTestResult.getThrowable() != null) {
            Allure.addAttachment("Error Details", iTestResult.getThrowable().toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.warn("Test OMITIDO: {}", getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // No es común usarlo, pero lo dejamos por completitud
    }
} 