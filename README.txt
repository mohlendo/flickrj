This is a Java API which wraps the REST-based Flickr API
(information available at http://www.flickr.com/services/api/).

This API has been tested with JDK 1.4.

To use the API just construct a instance of the class
com.aetreion.flickr.Flickr and request the interfaces which you need
to work with.  For example, to send a test ping to the Flickr service:

Flickr f = new Flickr();
TestInterface testInterface = f.getTestInterface();
Document document = testInterface.echo(Collections.EMPTY_LIST);
System.out.println("Document: " + document);

Comments and questions should be sent to anthonyeden@gmail.com.

-Anthony Eden