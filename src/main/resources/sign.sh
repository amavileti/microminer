#!/bin/sh
KEYSTORE=src/main/resources/signing-jar.keystore
keytool -genkey -alias applet -keystore $KEYSTORE -storepass applet -keypass applet -dname "CN=developer, OU=group 3, O=edu.csudh.cs.se, L=Carson, ST=CA, C=US"
keytool -selfcert -alias applet -keystore $KEYSTORE -storepass applet -keypass applet
