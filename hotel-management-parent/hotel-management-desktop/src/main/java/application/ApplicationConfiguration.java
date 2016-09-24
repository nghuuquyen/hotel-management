package application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderTablePane;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.controller.OrderRoomController;
import com.gsmart.ui.controller.QuickSearchRoomController;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Lazy
@Configuration
@ComponentScan(basePackages = {"com.gsmart"})
public class ApplicationConfiguration {
	
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen, 777, 500));
        primaryStage.show();
    }
    
    @Bean
    public OrderTablePane orderTablePane() {
    	return new OrderTablePane();
    }
	
	@Bean
	@Scope("prototype")
	public OrderRoomController orderRoomController() {
		return new OrderRoomController();
	}
	
	@Bean
	@Scope("prototype")
	public QuickSearchRoomController quickSearchRoomController() {
		return new QuickSearchRoomController();
	}
	
	@Bean
    @Scope("prototype")
    public FXMLDialog quickSearchRoomDialog() {
        return new FXMLDialog(quickSearchRoomController(), getClass()
        		.getResource("/com/gsmart/ui/components/QuickSearchRoomStage.fxml"), primaryStage
        		, new String[]{"css/quick-search-room.css","css/application.css"});
    }
	
	@Bean
    @Scope("prototype")
    public FXMLDialog orderRoomDialog() {
        return new FXMLDialog(orderRoomController(), getClass()
        		.getResource("/com/gsmart/ui/components/OrderRoomStage.fxml"), primaryStage
        		, new String[]{"css/order-room-stage.css","css/application.css"});
    }
	
	@Bean
    @Scope("singleton")
	public HomeController homeController() {
        return new HomeController();
    }
	
	@Bean
    @Scope("prototype")
	public FXMLDialog homeDialog() {
        return new FXMLDialog(homeController(), getClass().getResource("/com/gsmart/ui/components/Home.fxml")
        		, primaryStage , new String[]{"css/home.css" , "css/order-info.css" , "css/order-table-pane.css"
        				,"css/application.css"} );
    }
}
