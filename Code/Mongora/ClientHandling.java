package Mongora;

import TableJAvafx.Property;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.*;

class TotalSum {

    Double first;
    Double second;

    public TotalSum(Double first, Double second) {
        this.first = first;
        this.second = second;
    }

}

class DoughnutChart extends PieChart {

    private final Circle innerCircle;

    public DoughnutChart(ObservableList<Data> pieData) {
        super(pieData);

        innerCircle = new Circle();

        innerCircle.setFill(Color.WHITESMOKE);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);

        addInnerCircleIfNotPresent();
        updateInnerCircleLayout();
    }

    private void addInnerCircleIfNotPresent() {
        if (getData().size() > 0) {
            Node pie = getData().get(0).getNode();
            if (pie.getParent() instanceof Pane) {
                Pane parent = (Pane) pie.getParent();

                if (!parent.getChildren().contains(innerCircle)) {
                    parent.getChildren().add(innerCircle);
                }
            }
        }
    }

    private void updateInnerCircleLayout() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (PieChart.Data data : getData()) {
            Node node = data.getNode();

            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }

        innerCircle.setCenterX(minX + (maxX - minX) / 2);
        innerCircle.setCenterY(minY + (maxY - minY) / 2);

        innerCircle.setRadius((maxX - minX) / 4);
    }
}

public class ClientHandling extends Application {

    Scene Mainwindow;
    Scene Menue;
    Scene Budget;
    Scene History;
    Scene PieStage;
    Scene PieStage2;
    Scene LineStage;
    Scene BarStage;

    LocalDate ld;

    Double d;

    boolean flag = false;
    boolean comflag = false;
    boolean ratioflag = false;

    String month = "";
    String year = "";
    String category = "";

    Hashtable<String, Double> datatable = new Hashtable<>();

    String propertysString = "";
    String Realname = "";

    Stage primaryStage = new Stage();
    Socket clientSocket = null;

    private ObservableList<PieChart.Data> details = FXCollections.observableArrayList();

    TableView<Property> table;
    TableView<Property> table2;
    ComboBox<Integer> dayBox;
    ComboBox<String> monthBox;
    ComboBox<Integer> yearBox;
    ComboBox<String> categoryBox;
    TextField amountField;

    PrintWriter out;
    BufferedReader in;

    String inf;

    static Double dincome = 0.0;
    static Double doutcome = 0.0;

    String clientname;

    private PieChart chart;
    private LineChart chartline;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    private BarChart chartB;
    private CategoryAxis xAxisB;
    private NumberAxis yAxisB;

    public static void main(String[] args) {
        launch(args);
    }

    public void setInf(String inf) {
        this.inf = inf;
        System.out.println(this.inf);
    }

    public Parent createContentbar() {

        String[] array = inf.split("#");
        Vector<BarChart.Data> tab1 = new Vector();
        Vector<BarChart.Data> tab2 = new Vector();
        Vector<Double> dataVector = new Vector<>();
        Double d1, d2;

        int num = array.length;
        System.out.println(num);

        for (String k : array) {
            String[] array2 = k.split(" ");
            tab1.add(new BarChart.Data<>(array2[0], (d1 = Double.parseDouble(array2[1]))));
            tab2.add(new BarChart.Data<>(array2[0], (d2 = (Double.parseDouble(array2[1]) + Double.parseDouble(array2[2])) / d)));
            dataVector.addElement(d1);
            dataVector.addElement(d2);
        }

        Double maxValue = Collections.max(dataVector);
        Double minValue = Collections.min(dataVector);
        double higher = 1000 * (int) (maxValue.doubleValue() * 1.02 / 1000);
        double lower = 1000 * (int) (minValue.doubleValue() * 0.98 / 1000);
        //yAxis = new NumberAxis("Amount", lower, higher, 1000);

        xAxisB = new CategoryAxis();

        if (!comflag) {
            yAxisB = new NumberAxis("Amount", lower, higher, 10000.0d);
        } else {
            yAxisB = new NumberAxis("Amount", lower, higher, 1000.0d);
        }
        ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList(
                new BarChart.Series("  MY  ", FXCollections.observableArrayList(
                        tab1.get(0),
                        tab1.get(1),
                        tab1.get(2),
                        tab1.get(3),
                        tab1.get(4),
                        tab1.get(5),
                        tab1.get(6),
                        tab1.get(7),
                        tab1.get(8),
                        tab1.get(9),
                        tab1.get(10),
                        tab1.get(11)
                )),
                new BarChart.Series("   AVERAGE  ", FXCollections.observableArrayList(
                        tab2.get(0),
                        tab2.get(1),
                        tab2.get(2),
                        tab2.get(3),
                        tab2.get(4),
                        tab2.get(5),
                        tab2.get(6),
                        tab2.get(7),
                        tab2.get(8),
                        tab2.get(9),
                        tab2.get(10),
                        tab2.get(11)
                ))
        );
        chartB = new BarChart(xAxisB, yAxisB, barChartData, 25.0d);
        chartB.setTitle("COMPARE WITH AVERAGE       " + year + "  " + category);
        return chartB;
    }

    public Scene barStage() {
        HBox hName = new HBox(10);
        hName.setAlignment(Pos.TOP_RIGHT);
        Label l = new Label(Realname);
        l.setTextFill(Color.GREEN);
        l.setFont(new Font(20));
        hName.getChildren().addAll(l);

        Parent pp = createContentbar();
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        //Finishbutton
        Button finishButton = new Button("Back");
        finishButton.setOnAction(e -> {
            //finishbuttonClicked();
            primaryStage.setTitle("FINANCE MANAGEMENT SYSTEM");
            primaryStage.setScene(menueStage());
            primaryStage.show();
            comflag = false;
        });

        yearBox = new ComboBox<>();
        yearBox.getItems().addAll(2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
        yearBox.setPromptText("Year");

        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Income", "Bills", "Business", "Education", "Entertainment", "Fees", "Financial", "Food", "Gift", "Donation", "Technology", "Health", "Home", "Kids", "Pets", "Shopping", "Taxes", "Transfer", "Travel", "Uncategorized");
        categoryBox.setPromptText("Category");

        Button okbutton = new Button("  OK  ");
        okbutton.setOnAction(e -> {
            comflag = true;
            year = yearBox.getValue() + "";
            category = categoryBox.getValue();
            try {
                //out.println(clientname+"comparison&2016*Income");
                out.println(clientname + "comparison&" + yearBox.getValue() + "*" + categoryBox.getValue());
                d = Double.parseDouble(in.readLine());
                inf = in.readLine();
                System.out.println(inf);
                primaryStage.setTitle("COMPARISON");
                primaryStage.setScene(barStage());
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(yearBox, categoryBox, okbutton, finishButton);

        vBox.getChildren().addAll(hName, pp, box);
        return new Scene(vBox, 1300, 680);

    }

    public Parent createContentline() {
        xAxis = new NumberAxis("Month", 1, 12, 1);

        String[] array = inf.split("#");
        Vector<Double> datavector = new Vector<>();
        Vector<XYChart.Data> tab1 = new Vector();
        Vector<XYChart.Data> tab2 = new Vector();
        Double index = new Double(1);
        for (String k : array) {
            String[] array2 = k.split(" ");
            //index,Double.parseDouble(array2[1])
            tab1.add(new XYChart.Data<>(index, Double.parseDouble(array2[1])));
            datavector.addElement(Double.parseDouble(array2[1]));
            datavector.addElement(Double.parseDouble(array2[2]));
            tab2.add(new XYChart.Data<>(index, Double.parseDouble(array2[2])));
            index++;
        }
        Double maxValue = Collections.max(datavector);
        Double minValue = Collections.min(datavector);
        double higher = 1000 * (int) (maxValue.doubleValue() * 1.02 / 1000);
        double lower = 1000 * (int) (minValue.doubleValue() * 0.98 / 1000);
        yAxis = new NumberAxis("Amount", lower, higher, 1000);

        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList(
                new LineChart.Series<>("INCOME", FXCollections.observableArrayList(
                        tab1.get(0),
                        tab1.get(1),
                        tab1.get(2),
                        tab1.get(3),
                        tab1.get(4),
                        tab1.get(5),
                        tab1.get(6),
                        tab1.get(7),
                        tab1.get(8),
                        tab1.get(9),
                        tab1.get(10),
                        tab1.get(11)
                )),
                new LineChart.Series<>("EXPENSE", FXCollections.observableArrayList(
                        tab2.get(0),
                        tab2.get(1),
                        tab2.get(2),
                        tab2.get(3),
                        tab2.get(4),
                        tab2.get(5),
                        tab2.get(6),
                        tab2.get(7),
                        tab2.get(8),
                        tab2.get(9),
                        tab2.get(10),
                        tab2.get(11)
                ))
        );
        chartline = new LineChart(xAxis, yAxis, lineChartData);
        chartline.setTitle("UP  DOWN  TREND         " + year);
        return chartline;
    }

    public Scene lineStage() {

        HBox hName = new HBox(10);
        hName.setAlignment(Pos.TOP_RIGHT);
        Label l = new Label(Realname);
        l.setTextFill(Color.GREEN);
        l.setFont(new Font(20));
        hName.getChildren().addAll(l);

        Parent p = createContentline();
        VBox vBox = new VBox(0);
        vBox.setAlignment(Pos.CENTER);

        yearBox = new ComboBox<>();
        yearBox.getItems().addAll(2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
        yearBox.setPromptText("Year");

        Button okButton = new Button("   OK   ");
        okButton.setOnAction(e -> {
            String sdd = "" + yearBox.getValue();
            year = sdd;
            if (!sdd.equals("")) {
                try {
                    out.println(clientname + "updown&" + yearBox.getValue());
                    inf = in.readLine();
                    System.out.println(inf);
                    primaryStage.setTitle("UP DOWN");
                    primaryStage.setScene(lineStage());
                    primaryStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        Button finishButton = new Button("Back");
        finishButton.setOnAction(e -> {
            //finishbuttonClicked();
            primaryStage.setTitle("FINANCE MANAGEMENT SYSTEM");
            primaryStage.setScene(menueStage());
            primaryStage.show();
        });
        hBox.getChildren().addAll(yearBox, okButton, finishButton);
        vBox.getChildren().addAll(hName, p, hBox);

        return new Scene(vBox, 1300, 680);
    }

    DoughnutChart getDoughnutChart(ObservableList<PieChart.Data> pieChartData) {
        DoughnutChart chart = new DoughnutChart(pieChartData);
        if (!ratioflag) {
            chart.setTitle("ALL CATEGORY  " + month + "   " + year);
        } else {
            chart.setTitle("INCOME  VS  EXPENSE   " + month + "  " + year);
        }
        return chart;
    }

    public Scene pieStage2() {
        HBox hName = new HBox(10);
        hName.setAlignment(Pos.TOP_RIGHT);
        Label l1 = new Label(Realname);
        l1.setTextFill(Color.GREEN);
        l1.setFont(new Font(20));
        hName.getChildren().addAll(l1);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        Parent p = createContent2();

        HBox hb2 = new HBox(20);
        hb2.setAlignment(Pos.CENTER);

        Double rIncome = (dincome * 100) / (dincome + doutcome);
        double d = rIncome.doubleValue();
        int Ratio1 = (int) d;
        int Ratio2 = 100 - Ratio1;

        Label l = new Label("INCOME -> " + dincome + "/=  EXPENSE -> " + doutcome + "/=                 INCOME : EXPENSE -> " + Ratio1 + "%  : " + Ratio2 + "%");
        l.setFont(new Font(20));
        l.setTextFill(Color.BLUE);
        hb2.getChildren().addAll(l);

        HBox hb = new HBox(20);
        hb.setAlignment(Pos.CENTER);

        Button PieFinish = new Button("Back");
        hb.getChildren().addAll(PieFinish);
        PieFinish.setOnAction(e -> {
            ratioflag = false;
            primaryStage.setTitle("All Category   ");
            primaryStage.setScene(getPieStage());
            primaryStage.show();
        });

        if (!flag) {
            vBox.getChildren().addAll(hName, p, hb2, hb);
        } else {
            vBox.getChildren().addAll(hName, getDoughnutChart(generateData2(inf)), hb2, hb);
        }
        return new Scene(vBox, 1300, 680);

    }

    public static ObservableList<PieChart.Data> generateData(String inf) {

        Vector<PieChart.Data> tablepieDatas = new Vector<>();
        String[] first = inf.split("#");
        for (String second : first) {
            second = second.replace("*", " ");
            String[] third = second.split(" ");
            System.out.println(third[0] + "_" + third[1]);
            if (Double.parseDouble(third[1]) != 0.0 && !third[0].equals("Income")) {
                tablepieDatas.add(new PieChart.Data(third[0], Double.parseDouble(third[1])));
            }

        }

        return FXCollections.observableArrayList(tablepieDatas);

    }

    public static ObservableList<PieChart.Data> generateData2(String inf) {

        Vector<PieChart.Data> tablepieDatas = new Vector<>();
        Double income = 0.0;
        Double outcome = 0.0;
        String[] first = inf.split("#");
        for (String second : first) {
            second = second.replace("*", " ");
            String[] third = second.split(" ");
            System.out.println(third[0] + "_" + third[1]);

            if (third[0].equals("Income")) {
                income += Double.parseDouble(third[1]);
            } else if (Double.parseDouble(third[1]) != 0.0 && !third[0].equals("Income")) {
                outcome += Double.parseDouble(third[1]);
            }

        }

        PieChart.Data[] a = new PieChart.Data[2];
        a[0] = new PieChart.Data("Income", income);
        a[1] = new PieChart.Data("Expense", outcome);
        System.out.println(tablepieDatas);
        dincome = income;
        doutcome = outcome;

        return FXCollections.observableArrayList(a);

    }

    public Parent createContent() {
        chart = new PieChart(generateData(this.inf));
        chart.setTitle("ALL  CATEGORY  " + year + "   " + month);
        chart.setClockwise(false);
        return chart;
    }

    public Parent createContent2() {
        chart = new PieChart(generateData2(this.inf));
        chart.setClockwise(false);
        chart.setTitle("INCOME  VS  EXPENSE   " + year + "   " + month);
        return chart;
    }

    public Scene getPieStage() {

        HBox hName = new HBox(10);
        hName.setAlignment(Pos.TOP_RIGHT);
        Label l = new Label(Realname);
        l.setTextFill(Color.GREEN);
        l.setFont(new Font(20));
        hName.getChildren().addAll(l);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        HBox hb = new HBox(20);
        hb.setAlignment(Pos.CENTER);

        monthBox = new ComboBox<>();
        monthBox.getItems().addAll("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
        monthBox.setPromptText("Month");

        yearBox = new ComboBox<>();
        yearBox.getItems().addAll(2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
        yearBox.setPromptText("Year");

        Button ok = new Button("  OK  ");
        ok.setOnAction(e -> {
            month = monthBox.getValue();
            year = "" + yearBox.getValue();

            if (!yearBox.getValue().equals("") && !monthBox.getValue().equals("")) {
                flag = true;
                try {
                    out.println(clientname + "pie&" + monthBox.getValue() + " " + yearBox.getValue());
                    inf = in.readLine();
                    System.out.println(inf);
                    inf = inf.trim();
                    primaryStage.setTitle("ALL CATEGORY");
                    primaryStage.setScene(getPieStage());
                    primaryStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        Button pie2 = new Button("Ratio");
        pie2.setOnAction(e -> {
            ratioflag = true;
            primaryStage.setTitle("RATIO");
            primaryStage.setScene(pieStage2());
            primaryStage.show();
        });

        Button PieFinish = new Button("Back");
        hb.getChildren().addAll(yearBox, monthBox, ok, pie2, PieFinish);
        PieFinish.setOnAction(e -> {
            ratioflag = false;
            flag = false;
            primaryStage.setTitle("FINANCE MANAGEMENT SYSTEM");
            primaryStage.setScene(menueStage());
            primaryStage.show();
        });

        if (!flag) {
            vBox.getChildren().addAll(hName, createContent(), hb);
        } else {
            vBox.getChildren().addAll(hName, getDoughnutChart(generateData(inf)), hb);
        }
        return new Scene(vBox, 1300, 680);

    }

    public Scene HistoryStage() {

        HBox hName = new HBox(10);
        hName.setPadding(new Insets(10, 10, 10, 10));
        hName.setAlignment(Pos.TOP_RIGHT);
        Label l = new Label(Realname);
        l.setTextFill(Color.GREEN);
        l.setFont(new Font(20));
        hName.getChildren().addAll(l);

        HBox hName2 = new HBox(10);
        hName2.setPadding(new Insets(10, 10, 10, 10));
        hName2.setAlignment(Pos.CENTER);
        Label l2 = new Label("PREVIOUS BUDGET");
        l2.setTextFill(Color.YELLOW);
        l2.setFont(new Font(20));
        hName2.getChildren().addAll(l2);

        //day column
        TableColumn<Property, Integer> daycolumn = new TableColumn<>("DAY");
        daycolumn.setMinWidth(200);
        daycolumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        //month column
        TableColumn<Property, String> monthcolumn = new TableColumn<>("MONTH");
        monthcolumn.setMinWidth(200);
        monthcolumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        //year column
        TableColumn<Property, Integer> yearcolumn = new TableColumn<>("YEAR");
        yearcolumn.setMinWidth(200);
        yearcolumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        //category column
        TableColumn<Property, String> categorycolumn = new TableColumn<>("CATEGORY");
        categorycolumn.setMinWidth(200);
        categorycolumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        //amount column
        TableColumn<Property, Double> amountcolumn = new TableColumn<>("AMOUNT");
        amountcolumn.setMinWidth(200);
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        table2 = new TableView<>();
        table2.setItems(getPropertys2());
        table2.getColumns().addAll(daycolumn, monthcolumn, yearcolumn, categorycolumn, amountcolumn);
        table2.setPrefHeight(500);

        Button finishstateButton = new Button("Back");
        finishstateButton.setOnAction(e -> {
            primaryStage.setTitle("FINANCE MANAGEMENT SYSTEM");
            primaryStage.setScene(menueStage());
            primaryStage.show();
        });
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        hBox.getChildren().addAll(finishstateButton);

        VBox vBox = new VBox();
        Image imagem = new Image("ImageFile/pattern13.jfif");
        BackgroundSize bc = BackgroundSize.DEFAULT;

        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, true, true);

        BackgroundImage img = new BackgroundImage(imagem, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        Background background = new Background(img);
        vBox.setBackground(new Background(img));
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(hName, hName2, table2, hBox);

        return new Scene(vBox, 1300, 680);
    }

    public ObservableList<Property> getPropertys2() {
        ObservableList<Property> propertys = FXCollections.observableArrayList();
        inf = inf.replace("#", " ");
        inf = inf.trim();
        System.out.println(inf);
        String dd[] = inf.split(" ");
        for (String s : dd) {
            System.out.println(s);
            //System.out.println(new Property(s));
            propertys.add(new Property(s));
        }
        return propertys;
    }

    public Scene budgetStage() {

        HBox hName = new HBox(10);
        hName.setAlignment(Pos.TOP_RIGHT);
        Label l = new Label(Realname);
        l.setTextFill(Color.GREEN);
        l.setFont(new Font(20));
        hName.getChildren().addAll(l);

        HBox hName2 = new HBox(10);
        hName2.setPadding(new Insets(10, 10, 10, 10));
        hName2.setAlignment(Pos.CENTER);
        Label l2 = new Label("MAKE YOUR BUDGET");
        l2.setTextFill(Color.DARKSLATEGREY);
        l2.setFont(new Font(20));
        hName2.getChildren().addAll(l2);

        //day column
        TableColumn<Property, Integer> daycolumn = new TableColumn<>("DAY");
        daycolumn.setMinWidth(200);
        daycolumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        //month column
        TableColumn<Property, String> monthcolumn = new TableColumn<>("MONTH");
        monthcolumn.setMinWidth(200);
        monthcolumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        //year column
        TableColumn<Property, Integer> yearcolumn = new TableColumn<>("YEAR");
        yearcolumn.setMinWidth(200);
        yearcolumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        //category column
        TableColumn<Property, String> categorycolumn = new TableColumn<>("CATEGORY");
        categorycolumn.setMinWidth(200);

        categorycolumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        //amount column
        TableColumn<Property, Double> amountcolumn = new TableColumn<>("AMOUNT");
        amountcolumn.setMinWidth(200);
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        table = new TableView<>();
        table.setItems(getPropertys());
        table.getColumns().addAll(daycolumn, monthcolumn, yearcolumn, categorycolumn, amountcolumn);

        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Income", "Bills", "Business", "Education", "Entertainment", "Fees", "Financial", "Food", "Gift", "Donation", "Technology", "Health", "Home", "Kids", "Pets", "Shopping", "Taxes", "Transfer", "Travel", "Uncategorized");
        categoryBox.setPromptText("Category");

        DatePicker dp = new DatePicker();
        dp.setOnAction(e -> {
            ld = dp.getValue();
            System.out.println("Date Selected " + ld);
        });

        amountField = new TextField();
        amountField.setPromptText("Amount");
        amountField.setMinWidth(100);

        //Add button
        Button addButton = new Button("ADD");
        addButton.setOnAction(e -> {
            Property property = new Property();
            String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

            property.setDay(ld.getDayOfMonth());
            property.setMonth(months[ld.getMonthValue() - 1]);
            property.setYear(ld.getYear());
            property.setCategory(categoryBox.getValue());
            property.setAmount(Integer.parseInt(amountField.getText()));

            propertysString = propertysString.concat(property.toString());

            table.getItems().add(property);
            amountField.clear();
            categoryBox.setValue("Uncategorized");
        });

        Button deletButton = new Button("DELETE");
        deletButton.setOnAction(e -> deleteButtonClicked());

        Button editButton = new Button("EDIT");
        editButton.setOnAction(e -> {
            ObservableList<Property> propertyselected;

            propertyselected = table.getSelectionModel().getSelectedItems();
            Property pp = propertyselected.get(0);
            amountField.setText(pp.getAmount() + "");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
            Hashtable<String, Integer> monthbyint = new Hashtable<>();
            monthbyint.put("JAN", Integer.valueOf(1));
            monthbyint.put("FEB", Integer.valueOf(2));
            monthbyint.put("MAR", Integer.valueOf(3));
            monthbyint.put("APR", Integer.valueOf(4));
            monthbyint.put("MAY", Integer.valueOf(5));
            monthbyint.put("JUN", Integer.valueOf(6));
            monthbyint.put("JUL", Integer.valueOf(7));
            monthbyint.put("AUG", Integer.valueOf(8));
            monthbyint.put("SEP", Integer.valueOf(9));
            monthbyint.put("OCT", Integer.valueOf(10));
            monthbyint.put("NOV", Integer.valueOf(11));
            monthbyint.put("DEC", Integer.valueOf(12));
            String date = monthbyint.get(pp.getMonth()) + "/" + pp.getDay() + "/" + pp.getYear();
            LocalDate localDate = LocalDate.parse(date, formatter);
            dp.setValue(localDate);

            categoryBox.setValue(pp.getCategory());
            deleteButtonClicked();

        });

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(dp, categoryBox, amountField, addButton, editButton, deletButton);

        //Finishbutton
        Button finishButton = new Button("Back");
        finishButton.setOnAction(e -> {
            finishbuttonClicked();
            primaryStage.setTitle("FINANCE MANAGEMENT SYSTEM");
            primaryStage.setScene(menueStage());
            primaryStage.show();
        });

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.getChildren().addAll(finishButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hName, hName2, table, hBox, hBox1);

        return new Scene(vBox, 1300, 680);
    }

    public ObservableList<Property> getPropertys() {
        ObservableList<Property> propertys = FXCollections.observableArrayList();
        return propertys;

    }

    public void addButtonClicked() {
        Property property = new Property();

        property.setDay(dayBox.getValue());
        property.setMonth(monthBox.getValue());
        property.setYear(yearBox.getValue());
        property.setCategory(categoryBox.getValue());
        property.setAmount(Integer.parseInt(amountField.getText()));

        propertysString = propertysString.concat(property.toString());

        table.getItems().add(property);
        amountField.clear();
        categoryBox.setValue("Uncategorized");

    }

    public void deleteButtonClicked() {
        ObservableList<Property> propertyselected, allPropertys;
        allPropertys = table.getItems();
        propertyselected = table.getSelectionModel().getSelectedItems();

        propertyselected.forEach(allPropertys::remove);
    }

    public void finishbuttonClicked() {
        ObservableList<Property> propertys = table.getItems();
        Iterator<Property> iterator = propertys.iterator();
        String ss = "";
        while (iterator.hasNext()) {
            ss = ss.concat(iterator.next().toString());
        }
        out.println(clientname + "budget#" + ss);
    }

    public Scene menueStage() {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.TOP_RIGHT);
        Label l = new Label(Realname);
        l.setTextFill(Color.GREEN);
        l.setFont(new Font(20));
        hBox.getChildren().addAll(l);
        Label label;
        label = new Label("FINANCE MANAGEMENT SYSTEM");
        label.setFont(new Font("Cambria", 40));
        label.setTextFill(Color.RED);

        Text text = new Text();
        TextField textField = new TextField();
        textField.setText("FINANCE MANAGEMENT SYSTEM");
        textField.setFont(new Font(40));
        text.textProperty().bind(textField.textProperty());

        ImageView wImageView = new ImageView(new Image("ImageFile/moneybag.jpg"));
        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.getChildren().addAll(wImageView);

        Button UpDownButton = new Button("Up-Down");
        UpDownButton.setOnAction(e -> {
            try {
                label.setTextFill(Color.BLUE);
                out.println(clientname + "updown&2016");
                year = "2016";
                inf = in.readLine();
                System.out.println(inf);
                primaryStage.setTitle("UP DOWN");
                primaryStage.setScene(lineStage());
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button budgetButton = new Button("Budget");
        budgetButton.setOnAction(e -> {
            label.setTextFill(Color.DARKMAGENTA);
            primaryStage.setTitle("BUDGET SECTION");
            primaryStage.setScene(budgetStage());
            primaryStage.show();
        });
        Button historyButton = new Button("History");
        historyButton.setOnAction(e -> {
            try {
                out.println(clientname + "history");
                inf = in.readLine();
                System.out.println("read");
                inf = inf.trim();
                label.setTextFill(Color.CHOCOLATE);
                primaryStage.setTitle("HISTORY");
                primaryStage.setScene(HistoryStage());
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button PieButton = new Button("ALL CATEGORY");
        PieButton.setOnAction(e -> {
            try {
                System.out.println("Pie");
                month = "WHOLE YEAR";
                year = "2016";
                out.println(clientname + "pie");
                inf = in.readLine();
                System.out.println(inf);
                inf = inf.trim();
                label.setTextFill(Color.BROWN);
                primaryStage.setTitle("ALL CATEGORY");
                primaryStage.setScene(getPieStage());
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button comButton = new Button("Comparison");
        comButton.setOnAction(e -> {
            try {
                year = "2016";
                category = "Income";
                label.setTextFill(Color.DEEPPINK);
                out.println(clientname + "comparison&2016*Income");
                d = Double.parseDouble(in.readLine());
                inf = in.readLine();
                System.out.println(inf);
                primaryStage.setTitle("COMPARISON");
                primaryStage.setScene(barStage());
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            try {
                out.println(clientname + "logout");
                clientSocket.close();
                clientSocket = null;
                label.setTextFill(Color.BLUEVIOLET);
                primaryStage.setTitle("WELCOME");
                primaryStage.setScene(logStage());
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        HBox menueBox = new HBox(10);
        menueBox.setAlignment(Pos.CENTER);
        menueBox.getChildren().addAll(budgetButton, historyButton, UpDownButton, PieButton, comButton, logoutButton);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        //9 12 13
        Image imagem = new Image("ImageFile/pattern9.jfif");
        BackgroundSize bc = BackgroundSize.DEFAULT;

        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, true, true);

        BackgroundImage img = new BackgroundImage(imagem, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        Background background = new Background(img);
        vBox.setBackground(new Background(img));
        vBox.getChildren().addAll(hBox, label, imageBox, menueBox);

        return new Scene(vBox, 1300, 680);
    }

    public Scene logStage() {

        ImageView wImageView = new ImageView(new Image("ImageFile/finance.png"));
        HBox wHBox = new HBox(10);
        wHBox.setAlignment(Pos.CENTER);
        wHBox.getChildren().addAll(wImageView);

        Label welcomLabel = new Label("WELCOME");
        welcomLabel.setFont(new Font(20));

        HBox wBox = new HBox(10);
        wBox.setAlignment(Pos.CENTER);
        wBox.getChildren().add(welcomLabel);

        Label userLabel = new Label("Username: ");
        TextField userField = new TextField();
        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER);
        userBox.getChildren().addAll(userLabel, userField);

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        HBox passwordBox = new HBox(10);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            if (clientSocket == null) {
                try {
                    clientSocket = new Socket(InetAddress.getLocalHost(), 10008);
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
                    welcomLabel.setText("Network Error");
                }
            }
            welcomLabel.setTextFill(Color.BLUE);
            if (!userField.getText().equals("") && !passwordField.getText().equals("")) {
                try {
                    welcomLabel.setText("Registered");
                    System.out.println("Register");
                    clientname = userField.getText() + "*" + passwordField.getText() + "#";
                    out.println(clientname + "register&");
                    String inString = in.readLine();
                    if (inString.equals("register")) {
                        welcomLabel.setText("Registration Complete");
                    } else if (inString.equals("notregister")) {
                        welcomLabel.setText("This is Registered, Try another");
                    }
                    System.out.println(userField.getText() + "FFF" + passwordField.getText());
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                welcomLabel.setTextFill(Color.RED);
                welcomLabel.setText("WARNING!!!");
            }
        });
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (clientSocket == null) {
                try {
                    clientSocket = new Socket(InetAddress.getLocalHost(), 10008);
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
                    welcomLabel.setText("Network Error");
                }
            }
            welcomLabel.setTextFill(Color.GREEN);
            if (!userField.getText().equals("") && !passwordField.getText().equals("")) {

                try {
                    Realname = userField.getText();
                    welcomLabel.setText("Logged");
                    System.out.println("Login");
                    clientname = userField.getText() + "*" + passwordField.getText() + "#";
                    out.println(clientname + "login&");
                    String inString = in.readLine();
                    if (inString.equals("notlog")) {
                        welcomLabel.setText("You are not registered");
                    } else if (inString.equals("log")) {
                        primaryStage.setTitle("FINANCE MANAGEMENT SYSTEM");
                        primaryStage.setScene(menueStage());
                        primaryStage.show();
                    }
                    System.out.println(userField.getText() + "FFF" + passwordField.getText());
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                welcomLabel.setTextFill(Color.RED);
                welcomLabel.setText("WARNING!!!");
            }
        });
        HBox welcomeBox = new HBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.getChildren().addAll(registerButton, loginButton);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            System.exit(0);
        });
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        Image imagem = new Image("ImageFile/goina.jfif");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage img = new BackgroundImage(imagem, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(img);
        vBox.setBackground(new Background(img));
        vBox.getChildren().addAll(wImageView, wBox, userBox, passwordBox, welcomeBox);
        return new Scene(vBox, 1300, 680);

    }

    @Override
    public void start(Stage primaryStage2) throws Exception {
        this.primaryStage = primaryStage2;

        primaryStage.setTitle("WELCOME");
        primaryStage.setScene(logStage());
        primaryStage.show();
    }

}
