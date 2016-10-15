package com.gsmart.ui.components;

import java.util.Date;

import org.springframework.data.jpa.domain.Specifications;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.specification.OrdersSpecification;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.utils.FxDatePickerConverter;
import com.gsmart.ui.utils.JavaFXUtils;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OrderTablePane extends VBox {

	private HomeController homeController;
	private OrdersRepository ordersRepository;

	private TextField searchTextField = new TextField();

	private ToggleGroup group = new ToggleGroup();
	private RadioButton customerNameRadioBtn = new RadioButton("By name");
	private RadioButton roomNameRadioBtn = new RadioButton("By room");
	private RadioButton customerIdRadioBtn = new RadioButton("By ID");

	private DatePicker fromDate = new DatePicker();
	private DatePicker toDate = new DatePicker();
	private ComboBox<RoomCategory> roomType = new ComboBox<RoomCategory>();

	private TableView<Orders> table = new TableView<Orders>();

	private Button searchButton = new Button("Search");

	public OrderTablePane() {
		super();
		getStyleClass().add("order-table-pane");
		getChildren().add(getTopBar());
		getChildren().add(getTable());

		initSearchButtonListener();
		setPadding(new Insets(10));
	}

	public HBox getTopBar() {
		HBox hb = new HBox();
		VBox left = new VBox();
		VBox right = new VBox();

		// == Initialization Left Pane === //
		left.setPadding(new Insets(20, 0, 0, 0));

		searchTextField.setPromptText("Search ...");
		left.getChildren().add(searchTextField);

		customerNameRadioBtn.setToggleGroup(group);
		roomNameRadioBtn.setToggleGroup(group);
		customerIdRadioBtn.setToggleGroup(group);

		customerIdRadioBtn.setUserData("SEARCH_BY_ID");
		roomNameRadioBtn.setUserData("SEARCH_BY_ROOM_NAME");
		customerNameRadioBtn.setUserData("SEARCH_BY_NAME");

		customerNameRadioBtn.setSelected(true);

		HBox toggleRadioBtn = new HBox();
		toggleRadioBtn.getChildren().add(customerNameRadioBtn);
		toggleRadioBtn.getChildren().add(roomNameRadioBtn);
		toggleRadioBtn.getChildren().add(customerIdRadioBtn);

		left.getChildren().add(toggleRadioBtn);
		// == Initialization Left Pane === //

		// == Initialization Right Pane === //
		roomType.setPromptText("All room type");

		HBox rightTop = new HBox();
		rightTop.setPadding(new Insets(10));
		rightTop.setSpacing(20);

		Label fromDateLbl = new Label("From");
		Label toDateLbl = new Label("To");

		fromDateLbl.getStyleClass().add("control-label");
		toDateLbl.getStyleClass().add("control-label");

		fromDate.setPrefWidth(120);
		toDate.setPrefWidth(120);

		FxDatePickerConverter converter = new FxDatePickerConverter();

		fromDate.setConverter(converter);
		toDate.setConverter(converter);

		rightTop.getChildren().add(fromDateLbl);
		rightTop.getChildren().add(fromDate);
		rightTop.getChildren().add(toDateLbl);
		rightTop.getChildren().add(toDate);

		Label roomTypeLbl = new Label("Type");
		roomTypeLbl.getStyleClass().add("control-label");
		rightTop.getChildren().add(roomTypeLbl);
		rightTop.getChildren().add(roomType);

		searchButton.getStyleClass().add("button-raised");
		searchButton.setDefaultButton(true);
		searchButton.setMnemonicParsing(false);
		searchButton.setPadding(new Insets(10));
		Text materialDesignIcon = GlyphsDude.createIcon(MaterialDesignIcon.MAGNIFY, "2.5em");
		materialDesignIcon.setFill(Color.WHITE);
		searchButton.setGraphic(materialDesignIcon);

		rightTop.getChildren().add(searchButton);
		right.getChildren().add(rightTop);
		// == Initialization Right Pane === //

		hb.getChildren().add(left);
		hb.getChildren().add(right);

		return hb;
	}

	public void initSearchButtonListener() {
		searchButton.setOnAction((event) -> {
			search();
		});
	}

	public void search() {
		Orders orderSampler = new Orders();
		orderSampler.setRoom(new Room());

		switch (group.getSelectedToggle().getUserData().toString()) {
		case "SEARCH_BY_ID": {
			orderSampler.setCustomerId(JavaFXUtils.getTextFieldValue(searchTextField));
			break;
		}

		case "SEARCH_BY_NAME": {
			orderSampler.setCustomerName(JavaFXUtils.getTextFieldValue(searchTextField));
			break;
		}

		case "SEARCH_BY_ROOM_NAME": {
			orderSampler.getRoom().setName(JavaFXUtils.getTextFieldValue(searchTextField));
			break;
		}
		}

		orderSampler.setCreatedAt(JavaFXUtils.getDatePickerValue(fromDate));
		orderSampler.setCheckOutAt(JavaFXUtils.getDatePickerValue(toDate));
		orderSampler.getRoom().setRoomCategory(roomType.getValue());

		OrdersSpecification ordersSpecification = new OrdersSpecification(orderSampler);
		table.setItems(FXCollections
				.observableArrayList(getOrdersRepository().findAll(Specifications.where(ordersSpecification))));
	}

	@SuppressWarnings("unchecked")
	public TableView<Orders> getTable() {

		TableColumn<Orders, String> indexCol = new TableColumn<Orders, String>("ID");
		TableColumn<Orders, String> statusCol = new TableColumn<Orders, String>("Status");
		TableColumn<Orders, String> customerNameCol = new TableColumn<Orders, String>("Customer Name");
		TableColumn<Orders, String> roomNameCol = new TableColumn<Orders, String>("Room Name");
		TableColumn<Orders, Date> fromDateCol = new TableColumn<Orders, Date>("From Date");
		TableColumn<Orders, Date> toDateCol = new TableColumn<Orders, Date>("To Date");
		TableColumn<Orders, Integer> dateNumberCol = new TableColumn<Orders, Integer>("Date Number");

		indexCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("id"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("roomStatus"));
		customerNameCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("customerName"));
		roomNameCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("roomName"));
		fromDateCol.setCellValueFactory(new PropertyValueFactory<Orders, Date>("createdAt"));
		toDateCol.setCellValueFactory(new PropertyValueFactory<Orders, Date>("checkOutAt"));
		dateNumberCol.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("numberDate"));

		table.getColumns().addAll(indexCol, statusCol, customerNameCol, roomNameCol, fromDateCol, toDateCol,
				dateNumberCol);

		table.getSelectionModel().selectedIndexProperty().addListener((object) -> {
			Orders orderSelected = table.getSelectionModel().getSelectedItem();
			if (orderSelected != null) {
				getController().getOrderInfoPane().setOrderInfomation(orderSelected);
				getController().getCalculatorPane().setCalculatorInformation(orderSelected);
				getController().getRoomInfoPane().setRoomInformation(orderSelected);
			}
		});

		table.setMaxHeight(255);
		return table;
	}

	public Orders getSeletedOrder() {
		return table.getSelectionModel().getSelectedItem();
	}

	public TableView<Orders> getTableView() {
		return this.table;
	}

	public void setController(HomeController homeController) {
		this.homeController = homeController;
	}

	public HomeController getController() {
		return this.homeController;
	}

	public ComboBox<RoomCategory> getRoomType() {
		return roomType;
	}

	public OrdersRepository getOrdersRepository() {
		return ordersRepository;
	}

	public void setOrdersRepository(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}

}
