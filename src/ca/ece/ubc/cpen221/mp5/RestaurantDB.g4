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
STRING : '\"' WORDS '\"' | '\'' WORDS '\''; 
//STRING : ~[<>]+ ; 
RANGE : RANGE_BOUND [\.][\.] RANGE_BOUND ; 
LPAREN : [(] ;
RPAREN : [)] ;
IN_STR : 'in' ; 
CATEGORY_STR : 'category' ; 
NAME_STR : 'name' ;
RATING_STR : 'rating' ; 
PRICE_STR : 'price' ; 
fragment
RANGE_BOUND : [1-5] ;
WORDS : [A-Za-z0-9WHITESPACE]+ ; 
WHITESPACE : [ \t\r\n]+ -> skip ;


/*
 * These are the parser rules. They define the structures used by the parser.
 *    *** Antlr requires grammar nonterminals to be lowercase, like html, normal, and italic.
 * 
 */
 
 root : query EOF ; 
 query : andExp ( OR andExp ) * ;
 andExp : atom ( AND atom ) * ;
 atom : in | category | name | LPAREN query RPAREN ;
 // atom : in | category | rating | price | name | LPAREN query RPAREN ;
 in : 'in' LPAREN STRING RPAREN ; 
 category : 'category' LPAREN STRING RPAREN ; 
 //rating : 'rating' range ;
 //price : 'price' range ;
 name : 'name' LPAREN STRING RPAREN ;
 //range : LPAREN RANGE RPAREN ; 

