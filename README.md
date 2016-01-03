# jmeter-result-parser
View your JMeter Result in more generalised form on a Web page.

## JMeter Result Parser Plugin

This plugin will help you to view your Load Test Results on a Web Page. This plugin will parse the result file generated with JMeter
and display the aggreagte result it in an HTML table.Along with this, the plugin will show up Success VS Error Count graph and response time graph of each and every Test Step as well as detailed report of each and every Test of your Test Script.

## Usage

Can be used in combination with the Jenkins or any other Web Tool which can initiate the load test from Web UI.

## Features

Generate aggreate results in HTML output of certain statistics (minimum, maximum, average, median,90% Line and 99% Line) for response duration and response size
Shows the Graphical representation of Success Samples Vs Failure Samples
Shows Different HTTP error codes generated in the load test.
Shows the Graphical representation for Response time of every load test step.
Shows the detailed result of each and every sample in tabular format along with Graphical representation 

## How to Use this

Make a web archive(war) file of this project and host it in any Servlet container and access the ur of your project.If you are hosting it for your web project make sure you are providing the file name as parameter from your index page.

## Example Output

![Screenshot1.JPG](https://github.com/MayankSainiTk20/jmeter-result-parser/blob/master/Screenshot1.JPG)
