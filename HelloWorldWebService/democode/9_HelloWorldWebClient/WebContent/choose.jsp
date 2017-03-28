<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Hello World</title>
        <style>.error { color: red; } .success { color: green; }</style>
    </head>
    <body>
        <form action="sayHelloViaRest" method="post">
            <h1>HelloWorldWebClient</h1>
            <h2>Please choose:</h2>
            <a href="sayHelloViaRest.jsp">Say Hello using REST...</a>
            <br/>
            <br/>
            <a href="sayHelloViaSoap.jsp">Say Hello using SOAP...</a>
            <br/>
            <br/>
            <a href="startSmallTalkViaRest.jsp">Start a small talk using REST...</a>
            <br/>
            <br/>
            <a href="startSmallTalkViaSoap.jsp">Start a small talk using SOAP...</a>
        </form>
    </body>
</html>