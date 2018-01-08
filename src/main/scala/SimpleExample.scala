package com.github.cmonkey.morpheus

import com.zavtech.morpheus.frame.{DataFrame, DataFrameRow, DataFrameValue}
import com.zavtech.morpheus.array.Array
import com.zavtech.morpheus.viz.chart.Chart

object SimpleExample extends App{

  val csv = "http://infrastructure.gc.ca/alt-format/opendata/project-list-with-forcast-dates-liste-de-projets-avec-dates-prevu-en.csv"

  DataFrame.read().csv(csv).out.print(10)


  DataFrame.read.csv(IOUtils.filter(
    IOUtils.get("http://www.cra-arc.gc.ca/gncy/stts/itstb-sipti/2015/tbl4f-eng.csv"),
    "^ +?(\\d|Age|Under).*")).out.print(10)

  /*
  val zip = IOUtils.get("http://www20.statcan.gc.ca/tables-tableaux/cansim/csv/04270009-eng.zip")
  val visits = DataFrame.read().csv(IOUtils.unzip(zip)(0)).rows().select()
    (row: DataFrameRow[AnyRef, String]) => row.getValue("VISITS").toString.startsWith("Visits")

  val values = visits.col("Value").toArray
  val stateVisits = DataFrame.ofDoubles(
    visits.col("TRAV").toArray,
    Array.of("Value"),
    (value: DataFrameValue[AnyRef, String]) => values.getDouble(value.rowOrdinal))

  Chart.create.asHtml.withBarPlot(stateVisits, false, (chart) => {
    chart.plot.orient.horizontal
    chart.plot.axes.range(0).label.withText("Visits (1000s)")
    chart.plot.axes.domain.label.withText("State")
    chart.title.withText("US States Visited By Canadians in 2014")
    chart.legend.on
    chart.show
  })
  */
}
