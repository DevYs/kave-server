package devy.cave.server.controller;

import devy.cave.server.db.model.Channel;
import devy.cave.server.db.model.Contents;
import devy.cave.server.db.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {

    private final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") int pageNo, String searchWord, String channelNo, Model model) {
        List<Contents> contents = searchService.contentsList(pageNo, searchWord, channelNo);
        int searchTotal = searchService.searchContents(searchWord, channelNo).size();

        boolean isChannel = !channelNo.equals("0");
        model.addAttribute("searchTotal", searchTotal);
        model.addAttribute("searchChannel", isChannel ? searchService.getChannel(channelNo) : new Channel("0", "전체"));
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("contentsList", contents);
        model.addAttribute("contentsSize", contents.size());
        return "search";
    }

    @RequestMapping(value = "/api/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Contents> search(@RequestParam(defaultValue = "1") int pageNo, String searchWord, String channelNo) {
        logger.info("pageNo " + pageNo);
        logger.info("searchWord " + searchWord);
        logger.info("channelNo " + channelNo);
        return searchService.contentsList(pageNo, searchWord, channelNo);
    }

}