module com.grid.graph.main {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.web;
    requires spring.beans;
    requires spring.context;
    requires com.grid.graph.generator;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    exports com.grid.graph.main;
}