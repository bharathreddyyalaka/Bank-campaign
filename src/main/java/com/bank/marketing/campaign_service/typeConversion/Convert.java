package com.bank.marketing.campaign_service.typeConversion;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.Annotation;
import java.util.Set;

public class Convert implements AnnotationFormatterFactory {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of();
    }

    @Override
    public Parser<?> getParser(Annotation annotation, Class fieldType) {
        return null;
    }

    @Override
    public Printer<?> getPrinter(Annotation annotation, Class fieldType) {
        return null;
    }
}
