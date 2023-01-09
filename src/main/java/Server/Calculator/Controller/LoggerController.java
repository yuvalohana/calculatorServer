package Server.Calculator.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class LoggerController {

    @GetMapping("/logs/level")
    public String getRequest(@RequestParam("logger-name") String loggerName) {
        Logger logger = LogManager.getLogger(loggerName);

        if(logger == null)
            return "No such logger exists";

        return logger.getLevel().toString();
    }

    @PutMapping("/logs/level")
    public String putRequest(@RequestParam("logger-name") String loggerName, @RequestParam("logger-level") String loggerLevel) {

        Logger logger = LogManager.getLogger(loggerName);

        if(logger == null)
            return "No such logger exists";

        Level level = Level.getLevel(loggerLevel);

        if(level == null)
            return "No such level exists";
        Configurator.setLevel(loggerName, level);

        return level.toString();
    }
}
