package com.tests;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionVariable;
import com.example.constraintElements.Term;
import com.example.constraintElements.TermVariable;
import com.example.parser.Lexer;
import com.example.parser.TermParser;
import com.example.parser.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TermParserTests {

    @ParameterizedTest
    @CsvSource({
            "a",
            "b",
            "c"
    })
    public void test_function_constant(String constant) {
        Lexer lexer = new Lexer(constant);
        List<Token> tokens = lexer.tokenize();
        TermParser parser = new TermParser(tokens);
        Term term = parser.parseTerm();

        assertInstanceOf(FunctionApplication.class, term);

        FunctionApplication functionApplication = (FunctionApplication) term;

        assertEquals(constant.charAt(0), functionApplication.functionSymbol.getName());
        assertEquals(0, functionApplication.args.length);

        System.out.println(term);
    }

    @ParameterizedTest
    @CsvSource({
            "x",
            "y",
            "z"
    })
    public void test_variable(String variable) {
        Lexer lexer = new Lexer(variable);
        List<Token> tokens = lexer.tokenize();
        TermParser parser = new TermParser(tokens);
        Term term = parser.parseTerm();

        assertInstanceOf(TermVariable.class, term);

        TermVariable functionVariable = (TermVariable) term;

        assertEquals(variable.charAt(0), functionVariable.getName());

        System.out.println(term);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "f_u(a,b,x)",
            "g_u(b,k,x)",
    })
    public void test_unordered_function_symbol(String unorderedFucntion) {

        Term term = getTerm(unorderedFucntion);

        assertInstanceOf(FunctionApplication.class, term);

        FunctionApplication functionApplication = (FunctionApplication) term;

        assertEquals(unorderedFucntion.charAt(0), functionApplication.functionSymbol.getName());

        assertFalse(functionApplication.isOrdered());

        Term[] args = functionApplication.args;

        assertEquals(3, args.length);

        assertInstanceOf(FunctionApplication.class,args[0]);
        assertInstanceOf(FunctionApplication.class,args[1]);
        assertInstanceOf(TermVariable.class,args[2]);


        FunctionApplication functionApplication1 = (FunctionApplication) args[0];
        FunctionApplication functionApplication2 = (FunctionApplication) args[1];
        TermVariable termVariable = (TermVariable) args[2];

        assertEquals(functionApplication1.functionSymbol.getName(), unorderedFucntion.charAt(4));

        assertEquals(functionApplication2.functionSymbol.getName(), unorderedFucntion.charAt(6));

        assertEquals(termVariable.getName(), unorderedFucntion.charAt(8));


    }
    @ParameterizedTest
    @ValueSource(strings = {
            "f_o(a,b,x)",
            "g_o(b,k,x)",
    })
    public void test_ordered_function_symbol(String unorderedFucntion) {

        Term term = getTerm(unorderedFucntion);

        assertInstanceOf(FunctionApplication.class, term);

        FunctionApplication functionApplication = (FunctionApplication) term;

        assertEquals(unorderedFucntion.charAt(0), functionApplication.functionSymbol.getName());

        assertTrue(functionApplication.isOrdered());

        Term[] args = functionApplication.args;

        assertEquals(3, args.length);

        assertInstanceOf(FunctionApplication.class,args[0]);
        assertInstanceOf(FunctionApplication.class,args[1]);
        assertInstanceOf(TermVariable.class,args[2]);


        FunctionApplication functionApplication1 = (FunctionApplication) args[0];
        FunctionApplication functionApplication2 = (FunctionApplication) args[1];
        TermVariable termVariable = (TermVariable) args[2];

        assertEquals(functionApplication1.functionSymbol.getName(), unorderedFucntion.charAt(4));

        assertEquals(functionApplication2.functionSymbol.getName(), unorderedFucntion.charAt(6));

        assertEquals(termVariable.getName(), unorderedFucntion.charAt(8));


    }
    @ParameterizedTest
    @ValueSource(strings = {
            "f_o(f_o(b),a,x)",
            "g_o(f_u(b),k,x)",
    })
//    public void test_nested_function_application(String functionApp) {
//
//        Term term = getTerm(functionApp);
//
//        assertInstanceOf(FunctionApplication.class, term);
//
//        FunctionApplication functionApplication = (FunctionApplication) term;
//
//        assertEquals(functionApp.charAt(0), functionApplication.functionSymbol.getName());
//
//        assertTrue(functionApplication.isOrdered());
//
//        Term[] args = functionApplication.args;
//
//        assertEquals(3, args.length);
//
//        assertInstanceOf(FunctionApplication.class,args[0]);
//        assertInstanceOf(FunctionApplication.class,args[1]);
//        assertInstanceOf(TermVariable.class,args[2]);
//
//
//        FunctionApplication functionApplication1 = (FunctionApplication) args[0];
//        FunctionApplication functionApplication2 = (FunctionApplication) args[1];
//        TermVariable termVariable = (TermVariable) args[2];
//
//    }



    private Term getTerm(String context){
        Lexer lexer = new Lexer(context);

        List<Token> tokens = lexer.tokenize();
        TermParser parser = new TermParser(tokens);
        Term term = parser.parseTerm();
        return term;
    }
}
