package fund_application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.*;

public class StockSimulatorEx extends Application {

    // 필드 선언
    private User_info currentUser;
    private Vector<Stock_info> stockList;
    private Trade tradeSystem;
    private Label userInfoLabel;
    private Map<Stock_info, XYChart.Series<Number, Number>> chartData = new HashMap<>();

    // 정적 필드로 데이터 전달
    public static User_info staticUser;
    public static Vector<Stock_info> staticStocks;

    public StockSimulatorEx() {
        // 기본 생성자
    }

    @Override
    public void init() throws Exception {
        // 정적 필드에서 데이터 로드
        if (staticUser != null) this.currentUser = staticUser;
        if (staticStocks != null) this.stockList = staticStocks;
        
        // 기본값 설정 (테스트용)
        if (currentUser == null) {
            currentUser = new User_info("테스트", "test", "1234");
        }
        if (stockList == null) {
            stockList = new Vector<>();
            stockList.add(new Stock_info("테스트주식", 1000));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // GUI 컴포넌트 초기화
        LineChart<Number, Number> priceChart = initChart();
        ListView<Stock_info> stockListView = initStockList();
        userInfoLabel = new Label(getUserInfo());

        // 거래 패널 구성
        HBox tradePanel = createTradePanel(stockListView);

        // 메인 레이아웃
        VBox mainLayout = new VBox(10,
            new Label("실시간 주식 차트"),
            priceChart,
            new VBox(5,
                userInfoLabel,
                new Label("전체 주식 목록"),
                stockListView,
                tradePanel
            )
        );
        mainLayout.setStyle("-fx-padding: 15; -fx-background-color: #f0f0f0;");

        // 실시간 업데이트 시작
        initRealTimeUpdates();

        // 장면 설정
        Scene scene = new Scene(mainLayout, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Advanced Stock Trading System");
        primaryStage.show();
    }

    private LineChart<Number, Number> initChart() {
    	NumberAxis xAxis = new NumberAxis("시간", 0, 24, 1);
        NumberAxis yAxis = new NumberAxis("가격", 0, 5000, 500);;
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // 주식 역사 데이터 로드
        Map<String, List<Stock_history>> historyMap = loadStockHistory();

        stockList.forEach(stock -> {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(stock.Stock_name);

            // 과거 데이터 추가
            List<Stock_history> histories = historyMap.get(stock.Stock_name);
            if (histories != null) {
                histories.forEach(history -> 
                    series.getData().add(
                        new XYChart.Data<>(history.Stock_time, history.shp)
                    )
                );
            }

            lineChart.getData().add(series);
            chartData.put(stock, series);
        });

        return lineChart;
    }

    private ListView<Stock_info> initStockList() {
        ListView<Stock_info> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(stockList));
        
        listView.setCellFactory(lv -> new ListCell<Stock_info>() {
            @Override
            protected void updateItem(Stock_info item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setTextFill(Color.BLACK);
                } else {
                    setText(String.format("%s - %,d원", item.Stock_name, item.get_price()));
                    // 보유 여부에 따른 색상 변경
                    if (!currentUser.Owned_stock.containsKey(item.Stock_name)) {
                        setTextFill(Color.GRAY);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });
        return listView;
    }

    private HBox createTradePanel(ListView<Stock_info> stockListView) {
        TextField quantityInput = new TextField();
        quantityInput.setPromptText("수량 입력");
        
        Button buyBtn = new Button("매수");
        buyBtn.setStyle("-fx-base: #4CAF50;");
        
        Button sellBtn = new Button("매도");
        sellBtn.setStyle("-fx-base: #F44336;");

        // 매수/매도 핸들러
        buyBtn.setOnAction(e -> handleTransaction(stockListView, quantityInput, true));
        sellBtn.setOnAction(e -> handleTransaction(stockListView, quantityInput, false));

        return new HBox(15,
            new Label("거래 수량:"),
            quantityInput,
            buyBtn,
            sellBtn
        );
    }

    private void handleTransaction(ListView<Stock_info> listView, 
                                  TextField input, 
                                  boolean isBuy) {
        try {
            Stock_info selected = listView.getSelectionModel().getSelectedItem();
            int amount = Integer.parseInt(input.getText());
            
            if(selected == null) throw new Exception("주식을 선택하세요");
            if(amount <= 0) throw new Exception("1개 이상 입력하세요");

            if(isBuy) {
                tradeSystem.Buy_stock(currentUser, selected, amount);
            } else {
                tradeSystem.Sell_stock(currentUser, selected, amount);
            }
            updateUI();
            
        } catch (NumberFormatException ex) {
            showAlert("입력 오류", "숫자를 입력하세요");
        } catch (Exception ex) {
            showAlert("거래 오류", ex.getMessage());
        }
    }

    private void updateUI() {
        userInfoLabel.setText(getUserInfo());
        updateChartData();
    }

    private String getUserInfo() {
        return String.format("%s님 | 잔고: %,d원 | 총 자산: %,d원",
            currentUser.Get_Id(),
            currentUser.Get_balance(),
            calculateTotalAssets()
        );
    }
    private void updateChartData() {
        // 모든 주식의 실시간 데이터 업데이트
        stockList.forEach(stock -> {
            XYChart.Series<Number, Number> series = chartData.get(stock);
            if (series != null) {
                // 새 데이터 포인트 추가
                XYChart.Data<Number, Number> newData = new XYChart.Data<>(
                    currentUser.get_time(), 
                    stock.get_price()
                );
                
                // UI 스레드에서 차트 업데이트
                Platform.runLater(() -> {
                    series.getData().add(newData);
                    
                    // 20개 이상 데이터 포인트 시 가장 오래된 항목 제거
                    if (series.getData().size() > 20) {
                        series.getData().remove(0);
                    }
                });
            }
        });
    }


    private int calculateTotalAssets() {
        return currentUser.Get_balance() + 
            stockList.stream()
                    .mapToInt(stock -> 
                        stock.get_price() * 
                        currentUser.Owned_stock.getOrDefault(stock.Stock_name, 0))
                    .sum();
    }

    private void initRealTimeUpdates() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> 
            Platform.runLater(() -> {
                stockList.forEach(stock -> {
                    stock.Stock_change(2, 1);
                    updateChartSeries(stock);
                });
            }), 0, 3, TimeUnit.SECONDS);
    }

    private void updateChartSeries(Stock_info stock) {
        XYChart.Series<Number, Number> series = chartData.get(stock);
        if(series != null) {
            series.getData().add(new XYChart.Data<>(
                currentUser.get_time(), 
                stock.get_price()
            ));
            if(series.getData().size() > 20) {
                series.getData().remove(0);
            }
        }
    }

    private Map<String, List<Stock_history>> loadStockHistory() {
        Map<String, List<Stock_history>> historyMap = new HashMap<>();
        List<Stock_history> allHistory = Stock_history.load();
        
        allHistory.forEach(history -> 
            historyMap.computeIfAbsent(history.StockName, 
                k -> new ArrayList<>()).add(history)
        );
        
        return historyMap;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
