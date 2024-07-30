package pet.store.controller.error;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.*;
import org.springframework.web.context.*;
import org.springframework.web.context.request.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice

@Slf4j
public class GlobalErrorHandler {
private enum LogStatus {
STACK_TRACE,MESSAGE_ONLY
}

@Data
private class ExceptionMessage {
private String message;
private String statusReason;
private int statusCode;
private String timestamp;
private String uri;
}
@ExceptionHandler(NoSuchElementException.class)
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public ExceptionMessage handleNoSuchElementException (NoSuchElementException ex, WebRequest webRequest){
return buildExceptionMessage(ex, webRequest);
}
private ExceptionMessage buildExceptionMessage (Exception ex, WebRequest webRequest) {
String message = ex.toString();
String statusReason = HttpStatus.NOT_FOUND.getReasonPhrase();
int statusCode = HttpStatus.NOT_FOUND.value();
String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
String uri = null;

if(webRequest instanceof ServletWebRequest swr) {
uri = swr.getRequest().getRequestURI();
}
log.error("Exception: {}", message);

ExceptionMessage exMsg = new ExceptionMessage ();

exMsg.setMessage(message);
exMsg.setStatusReason(statusReason);
exMsg.setStatusCode(statusCode);
exMsg.setTimestamp(timestamp);
exMsg.setUri(uri);

return exMsg;
	} 
}