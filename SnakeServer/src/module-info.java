/**
 * 
 */
/**
 * @author etud
 *
 */
module SnakeServer {
	requires RequestsLib;
	requires org.junit.jupiter.api;
	requires java.desktop;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	exports utils.items;
	exports utils;
}