# Clojure Script Lexer  

This repository contains the implementation of a lexer in Java for Clojure Script language.   
The lexer was created based on the official documentation of the language, which can be found [here](https://clojure.org/index)  

After examining the syntax of the language, the following **list of tokens** was created:  
1.Operator  
2.Identifier  
3.Keyword  
4.Number  
5.String  
6.Boolean  
7.Comment  
Please note that this list may not be entirely accurate, as the language documentation does not provide a clear description of all lexemes.  

## Functionality  
The repository includes the ClojureScriptLexer class which provides the functionality of tokenizing input code into a list of tokens.   
The main function lex(String code) returns an array of tokens.  
The processing is done character by character, and depending on the character, helper functions such as lexNumber, lexString, lexIdentifier are called to analyze the following characters to check for compliance with the token.

## Errors  
The implementation includes the handling of the following lexical errors, after encountering which the program stops execution:
- Unknown character
- Incorrect number: 234dsdsa33
- Incorrect string literal: "dsdsdsa
Additionally, arrays of keywords and operators in the language were created, found in the [short description](https://cljs.info/cheatsheet/)

## Input Data
The input data is a file file.txt containing a sample of Clojure Script code.
