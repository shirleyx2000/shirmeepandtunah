grammar RestaurantDB;

// This puts "package ca.ece.ubc.cpen221.mp5;" at the top of the output Java files.
@header {
package ca.ece.ubc.cpen221.mp5;
}

// This adds code to the generated lexer and parser. Do not change these lines.
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a RuntimeException.
    public void reportErrorsAsExceptions() {
        //removeErrorListeners();
        
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *   *** Antlr requires tokens to be CAPITALIZED, like START_ITALIC, END_ITALIC, and TEXT.
 */

OR : '||' ;
AND : '&&' ;
STRING : ( '\"' | '\'' )+ [a-zA-Z0-9 ]+ ( '\"' | '\'' )+ ; 
RANGE_BOUND : [1-5] ; 
RANGE_DOUBLE : [1-5] ([\.][0-9])? ;
RANGE : '..' ; 
LPAREN : '(' ;
RPAREN : ')' ; 
WHITESPACE : [ \t\r\n]+ -> skip ;

/*
 * These are the parser rules. They define the structures used by the parser.
 *    *** Antlr requires grammar nonterminals to be lowercase, like html, normal, and italic.
 * 
 */
 
 root : query EOF ; 
 query : andExp ( OR andExp ) * ;
 andExp : atom ( AND atom ) * ;
 atom : in | category | rating | price | name | LPAREN query RPAREN ;
 in : 'in' LPAREN STRING RPAREN ; 
 category : 'category' LPAREN STRING RPAREN ; 
 rating : 'rating' LPAREN ( RANGE_DOUBLE | RANGE_BOUND ) RANGE ( RANGE_DOUBLE | RANGE_BOUND ) RPAREN ;
 price : 'price' LPAREN RANGE_BOUND RANGE RANGE_BOUND RPAREN ;
 name : 'name' LPAREN STRING RPAREN ;
 //range : LPAREN RANGE_BOUND RANGE RANGE_BOUND RPAREN ; 


