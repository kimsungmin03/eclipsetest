/**
 * 
 */
/**
 * @author 김성민
 *
 */
module fund_application {
    requires javafx.controls;
    requires javafx.base;
    
    exports fund_application to javafx.graphics;
    opens fund_application to javafx.base;
}