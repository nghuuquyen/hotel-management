package com.gsmart.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomInfoPane extends VBox{
		
	private TextField roomNameTxt = new TextField();
	private TextField priceTxt = new TextField();
	private TextField acreageTxt = new TextField();
	private TextField roomTypeTxt = new TextField();
	private TextField capacityTxt = new TextField();
	
	public RoomInfoPane() {
		super();
		setPrefWidth(320);
		setSpacing(10);
		getStyleClass().add("content-block-background");
		
		getChildren().add(getTopBar());
		getChildren().add(getContent());
	}
	
	public HBox getTopBar() {
		HBox hb = new HBox();
		VBox vb = new VBox();
		//Set Style CSS.
		hb.getStyleClass().add("top-bar");
		hb.setPadding(new Insets(5, 5, 5, 5));
		
		Label label = new Label("Room Information");
		label.getStyleClass().add("header-label");
		vb.setPrefWidth(200);
		vb.getChildren().add(label);
				
		hb.getChildren().add(vb);
		return hb;
	}
	
	public VBox getContent() {
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.setPadding(new Insets(10, 10 , 10 , 10));
		
		vb.getChildren().add(getRowField("Room name", roomNameTxt, "", "#000000"));
		vb.getChildren().add(getRowField("Price", priceTxt, "/ Date" , "#000000" ));
		vb.getChildren().add(getRowField("Acreage", acreageTxt, "m²" , "#000000"));
		vb.getChildren().add(getRowField("Room Type", roomTypeTxt, "", "#000000"));
		vb.getChildren().add(getRowField("Capacity", capacityTxt, "Person", "#000000"));
		
		return vb;
	}
	
	public HBox getRowField(String label, TextField textField, String unitName , String textColor){
		Label _label = new Label(label);
		_label.setPrefWidth(100);
		
		Label _unitName = new Label(unitName);
		
		textField.setStyle("-fx-text-fill:" + textColor + ";");
		textField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		
		HBox hb = new HBox();
		hb.setSpacing(5);
		
		hb.getChildren().add(_label);
		hb.getChildren().add(textField);
		hb.getChildren().add(_unitName);
		
		return hb;
	}
}