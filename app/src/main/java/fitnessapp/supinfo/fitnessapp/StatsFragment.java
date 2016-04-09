package fitnessapp.supinfo.fitnessapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Toast;
import fitnessapp.supinfo.fitnessapp.dao.implemented.RunnerDAOImpl;
import fitnessapp.supinfo.fitnessapp.model.Runner;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {


        private ComboLineColumnChartView chart;
        private ComboLineColumnChartData data;
        private int numberOfLines = 1;
        private int maxNumberOfLines = 4;
        private int numberofWeights = 12;
        private RunnerDAOImpl rDAO;
        private int BLEU = Color.parseColor("#3F51B5");


        ArrayList<Runner> runners;

        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasPoints = true;
        private boolean hasLines = true;
        private boolean isCubic = false;
        private boolean hasLabels = true;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            this.rDAO = new RunnerDAOImpl(this.getActivity());
            View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
            numberofWeights = this.rDAO.getAllWeights().size();
            runners = this.rDAO.getAllWeights();

            chart = (ComboLineColumnChartView) rootView.findViewById(R.id.weightstats);
            chart.setOnValueTouchListener(new ValueTouchListener());

            generateData();

            return rootView;
        }


        private void generateData() {

            data = new ComboLineColumnChartData(generateColumnData(), generateLineData());

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("Jour : ");
                    axisY.setName("KG : ");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setComboLineColumnChartData(data);
        }

        private LineChartData generateLineData() {

            List<Line> lines = new ArrayList<Line>();
            for (int i = 0; i < numberOfLines; ++i) {

                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j < numberofWeights; ++j) {
                    int w = Integer.parseInt(this.runners.get(j).getWeight());
                    values.add(new PointValue(j, w));
                }

                Line line = new Line(values);
                line.setColor(ChartUtils.COLORS[i]);
                line.setCubic(isCubic);
                line.setHasLabels(hasLabels);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
                lines.add(line);
            }

            LineChartData lineChartData = new LineChartData(lines);

            return lineChartData;

        }

        private ColumnChartData generateColumnData() {
            int numSubcolumns = 1;

            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numberofWeights; ++i) {
                int w = Integer.parseInt(this.runners.get(i).getWeight());
                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {

                    values.add(new SubcolumnValue(w, BLEU));
                }

                columns.add(new Column(values));
            }

            ColumnChartData columnChartData = new ColumnChartData(columns);
            return columnChartData;
        }

        private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {

            @Override
            public void onValueDeselected() {

            }


            @Override
            public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
            }
        }
    }

