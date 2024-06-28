package com.metene.domain.entity;

/**
 * @author jmetenen
 */
public enum ComplienceType {
    MULTI_LEVEL,       // Consentimiento con los botones "DENY", "ALLOW SELECTION" y "ALLOW ALL"
    ACEPT_ONLY,        // Consentimiento solo con el botón de "ALLOW ALL"
    ACCEPT_OR_DECLINE  // Consentimiento con los botones "DENY" y "ALLOW ALL"
}
