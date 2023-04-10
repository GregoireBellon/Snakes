/**
 * 
 */
/**
 * @author etud
 *
 */
module SnakeServer {
	requires transitive RequestsLib;
	requires org.junit.jupiter.api;
	requires transitive java.desktop;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	
	exports behavior.searchPathAlgorithm;
	exports utils.items;
	exports utils;
	exports behavior;
	exports core;
	exports core.event;
	exports core.event.handler;
	exports controller;
}