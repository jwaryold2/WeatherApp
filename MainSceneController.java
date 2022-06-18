package application;

import application.Main;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class MainSceneController {
	private static HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.followRedirects(HttpClient.Redirect.NORMAL).build();
	private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private String key = "4UvWAaof46Bm7DxUV3encQtydzEHgLqs";
	String cityName;
	String url2;
	String url;
	Image main = new Image("https://media.giphy.com/media/Z9K5FBE4enqFrj0flE/giphy.gif");
	@FXML
	private TextField bar;
	@FXML
	private Label ins = new Label("Enter the Name of the City to get Weather");
	@FXML
	private Button searched;
	@FXML
	private ImageView city = new ImageView(main);
	@FXML
	private TextField feelsx = new TextField(""+0);
	@FXML
	private TextField tempx= new TextField(""+0);
	@FXML
	private TextField minx= new TextField(""+0);
	@FXML
	private TextField maxx= new TextField(""+0);
	// Event Listener on Button[#searchB].onAction
	@FXML
	public void search(ActionEvent event) {
		try {
			//mainWindow.setResizable(false);
			//ins.setText("Enter the Name of the City to get Weather");
			cityName = bar.getText();
			url2 = "https://www.mapquestapi.com/staticmap/v5/map?key=" + key + "&center=" + cityName
					+ "&size=600,400@2x";
			url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName
					+ "&APPID=9df7b4ccfa993b0d3ab4969ccc18ec98";
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
			HttpResponse<String> response = HTTP_CLIENT.<String>send(request, BodyHandlers.ofString());
			String body = response.body();
			WeatherResponse we = GSON.fromJson(body, WeatherResponse.class);
			for (int i = 0; i < we.weather.length; i++) {
				double tempF = 1.8 * (we.main.temp - 273.15) + 32;
				double feelsF = 1.8 * (we.main.feels_like - 273.15) + 32;
				double min = 1.8 * (we.main.temp_min - 273.15) + 32;
				double max = 1.8 * (we.main.temp_max - 273.15) + 32;
				feelsx.setText(""+Math.ceil(feelsF));
				tempx.setText(""+Math.ceil(tempF));
				minx.setText(""+Math.ceil(min));
				maxx.setText(""+Math.ceil(max));
				city.setImage(new Image(url2));
			}
			
		}catch (Exception e) {
			System.err.println(e);
		}
	}
}
