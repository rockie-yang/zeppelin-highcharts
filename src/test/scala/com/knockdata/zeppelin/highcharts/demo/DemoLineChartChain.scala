/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.knockdata.zeppelin.highcharts.demo

import com.knockdata.zeppelin.highcharts.model._
import com.knockdata.zeppelin.highcharts._

import org.apache.spark.sql.functions._
import org.junit.Test

// # Line Chart Demo
//
// Based on [Line Chart Demo](http://www.highcharts.com/demo/line-basic)
//
class DemoLineChartChain {
  val bank = DataSet.dfBank

  // ## Line Chart Basic
  //
  // Based on [Highcharts Demo Line Basic](http://www.highcharts.com/demo/line-basic)
  //
  // an line chart with
  //
  // * x axis data from column $"age"
  // * y axis aggregated the average balance
  // * data point order by age
  //
  @Test
  def demoLineBasic: Unit = {
    highcharts(bank)
      .series("x" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age")).plot()

  }

  // ## Line Chart Basic, Explicitly Ascending Order
  //
  // Based on [Highcharts Demo Line Basic](http://www.highcharts.com/demo/line-basic)
  //
  // an line chart with
  //
  // * x axis data from column $"age"
  // * y axis aggregated the average balance
  // * data point order by age, specified EXPLICITLY ascending order
  //
  @Test
  def demoLineBasicAsc: Unit = {

    highcharts(bank)
      .series("x" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age").asc).plot()
  }

  // ## Line Chart Basic, Descending Order
  //
  // Based on [Highcharts Demo Line Basic](http://www.highcharts.com/demo/line-basic)
  //
  // an line chart with
  //
  // * x axis data from column $"age"
  // * y axis aggregated the average balance
  // * data point order by age, descending order
  //
  @Test
  def demoLineBasicDesc: Unit = {

    highcharts(bank)
      .series("name" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age").desc)
      .xAxis(new XAxis("age").typ("category"))
      .plot()

  }

  // ## Line Chart Multiple Series
  //
  // Based on [Highcharts Demo Line Basic](http://www.highcharts.com/demo/line-basic)
  //
  // an line chart with
  //
  // * create multiple series according to $"marital" column
  // * x axis data from column $"age"
  // * y axis aggregated the average balance
  // * data point order by age, descending order
  //
  @Test
  def demoLineBasicMultipleSeriesWithoutOption: Unit = {
    highcharts(bank).seriesCol("marital")
      .series("name" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age"))
      .plot()
  }

  // ## Line Chart Multiple Series, With Options
  //
  // Based on [Highcharts Demo Line Basic](http://www.highcharts.com/demo/line-basic)
  //
  // an line chart with
  //
  // * create multiple series according to $"marital" column
  // * x axis data from column $"age"
  // * y axis aggregated the average balance
  // * data point order by age
  //
  @Test
  def demoLineBasicMultipleSeriesWithOption: Unit = {
    highcharts(bank).seriesCol("marital")
      .series("name" -> "age",
        "y" -> avg(col("balance")))
      .orderBy(col("age"))
      .title(new Title("Marital Job Average Balance").x(-20))
      .subtitle(new Subtitle("Source: Zeppelin Tutorial").x(-20))
      .xAxis(new XAxis("Age").typ("category"))
      .yAxis(new YAxis("Balance(¥)").plotLines(
        Map("value" -> 0, "width" -> 1, "color" -> "#808080")))
      .tooltip(new Tooltip().valueSuffix("¥"))
      .legend(new Legend().layout("vertical").align("right")
        .verticalAlign("middle").borderWidth(0))
      .plot()

  }

  // ## Line Chart, With Data Labels
  //
  // Based on [Highchart Line Charts Demo With data labels](http://www.highcharts.com/demo/line-labels)
  //
  // an line chart with
  //
  // * name data(xAxis) from column $"name"
  // * y axis aggregated the average balance
  // * data point order by $"job"
  //
  @Test
  def demoLineWithDataLabels: Unit = {
    highcharts(bank).series("name" -> "job", "y" -> avg(col("balance")))
      .orderBy(col("job"))
      .plotOptions(new plotOptions.Line()
        .dataLabels("enabled" -> true, "format" -> "{point.y:.2f}"))
      .tooltip(new Tooltip().valueDecimals(2)).plot()

  }

  // ## Line Chart Zoomable
  //
  // Based on [Highchart Line Charts Zoomable](http://www.highcharts.com/demo/line-time-series)
  //
  // an line chart with
  //
  // * name data(xAxis) from column $"age"
  // * y axis aggregated the average balance
  // * data point order by $"job"
  //
  //
  // NOTE:
  // linearGradient is not described in [Highcharts API](http://api.highcharts.com/highcharts#plotOptions.area.fillColor)
  //
  @Test
  def demoLineZoomable: Unit = {

    val options = new plotOptions.Area()
      .fillColorLinearGradient("x1" -> 0, "y1" -> 0, "x2" -> 0, "y2" -> 1)
      .fillColorStops((0, "Highcharts.getOptions().colors[0]"),
          (1, "Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')"))

    highcharts(bank).series("name" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age"))
      .chart(new Chart("area").zoomType("x"))
      .plotOptions(options).plot()
  }

  // ## Spline Inverted
  //
  // Based on [Highchart Spline Line Inverted](http://www.highcharts.com/demo/spline-inverted)
  //
  // an line chart with
  //
  // * name data(xAxis) from column $"age"
  // * y axis aggregated the average balance
  // * data point order by $"job"
  //
  @Test
  def demoSplineInverted: Unit = {
    highcharts(bank).series("x" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age"))
      .chart(new Chart("spline").inverted(true))
      .plot()
  }

  @Test
  def demoSplineWithSymbols: Unit = {
    // TODO
  }

  // ## Spline With Plot Bands
  //
  // Based on [Spline Plot Bands](http://www.highcharts.com/demo/spline-plot-bands)
  //
  // an line chart with
  //
  // * name data(xAxis) from column $"age"
  // * y axis aggregated the average balance
  // * data point order by $"job"
  //
  @Test
  def demoSplineWithPlotBands: Unit = {
    val yAxis = new YAxis("Average Balance").plotBands(
      Map("from" -> 0, "to" -> 1000, "color" -> "rgba(68, 170, 213, 0.1)",
        "label" -> Map(
          "text" -> "Low",
          "style" -> Map(
            "color" -> "#606060"
          )
        )
      ),
      Map("from" -> 5000, "to" -> 10000, "color" -> "rgba(68, 170, 213, 0.1)",
        "label" -> Map(
          "text" -> "High",
          "style" -> Map(
            "color" -> "#606060"
          )
        )
      )
    )
    highcharts(bank).series("x" -> "age", "y" -> avg(col("balance")))
      .orderBy(col("age"))
      .yAxis(yAxis)
      .plot()
  }

  // ## Time Data With Irregular Intervals
  //
  // Based on [Time Data With Irregular Intervals](http://www.highcharts.com/demo/spline-irregular-time)
  //
  // an line chart with
  //
  // * multiple series using column $"year"
  // * x axis from $"time"
  // * y axis using $"depth"
  //
  @Test
  def demoTimeDataWithIrregularIntervals: Unit = {
    highcharts(DataSet.dfSnowDepth).seriesCol("year")
      .series("x" -> "time", "y" -> "depth")
      .chart(new Chart("spline"))
      .title(new Title("Snow depth at Vikjafjellet, Norway"))
      .subtitle(new Subtitle("Irregular time data in Highcharts JS"))
      .xAxis(new XAxis("Date").typ("datetime").dateTimeLabelFormats(
        "month" -> "%e. %b", "year" -> "%b"))
      .yAxis(new YAxis("Snow depth (m)").min(0))
      .tooltip(new Tooltip().headerFormat("<b>{series.name}</b><br>").pointFormat(
        "{point.x:%e. %b}: {point.y:.2f} m"))
      .plotOptions(new plotOptions.Spline().marker("enabled" -> true))
      .plot()
  }
}
