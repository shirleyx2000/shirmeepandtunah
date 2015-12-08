// Generated from RestaurantDB.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5;

import java.util.Collection;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RestaurantDBParser}.
 */
public interface RestaurantDBListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#andExp}.
	 * @param ctx the parse tree
	 */
	void enterAndExp(@NotNull RestaurantDBParser.AndExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#andExp}.
	 * @param ctx the parse tree
	 */
	void exitAndExp(@NotNull RestaurantDBParser.AndExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#in}.
	 * @param ctx the parse tree
	 */
	void enterIn(@NotNull RestaurantDBParser.InContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#in}.
	 * @param ctx the parse tree
	 */
	void exitIn(@NotNull RestaurantDBParser.InContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#price}.
	 * @param ctx the parse tree
	 */
	void enterPrice(@NotNull RestaurantDBParser.PriceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#price}.
	 * @param ctx the parse tree
	 */
	void exitPrice(@NotNull RestaurantDBParser.PriceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(@NotNull RestaurantDBParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(@NotNull RestaurantDBParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(@NotNull RestaurantDBParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(@NotNull RestaurantDBParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#rating}.
	 * @param ctx the parse tree
	 */
	void enterRating(@NotNull RestaurantDBParser.RatingContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#rating}.
	 * @param ctx the parse tree
	 */
	void exitRating(@NotNull RestaurantDBParser.RatingContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(@NotNull RestaurantDBParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(@NotNull RestaurantDBParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(@NotNull RestaurantDBParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(@NotNull RestaurantDBParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link RestaurantDBParser#category}.
	 * @param ctx the parse tree
	 */
	void enterCategory(@NotNull RestaurantDBParser.CategoryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RestaurantDBParser#category}.
	 * @param ctx the parse tree
	 */
	void exitCategory(@NotNull RestaurantDBParser.CategoryContext ctx);
}