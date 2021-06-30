package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    EventCategoryRepository eventCategoryRepository;

    @Autowired
    TagRepository tagRepository;

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model) {
        if (categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isEmpty()){
                model.addAttribute("title","Invalid Category ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                model.addAttribute("title","Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
        }

        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors,Model model) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            model.addAttribute("types",eventCategoryRepository.findAll());
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Event");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds){
        if(eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("detail")
    public String showEventDetails(@RequestParam Integer eventID, Model model){

        Optional<Event> result = eventRepository.findById(eventID);

        if (result.isEmpty()){
            model.addAttribute("title", "Invalid Event ID: " + eventID);
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            model.addAttribute("tags",event.getTags());
        }

        return "events/detail";
    }

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();

        model.addAttribute("title", "Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);
        return "events/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Errors errors, Model model){
        if(!errors.hasErrors()){
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventID=" + event.getId();
        }

        return "redirect:add-tag";
    }
    /*
    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId){
        Event eventToEdit = eventRepository.findById(eventId);
        model.addAttribute("event", eventToEdit);
        String title = "Edit Event" + eventToEdit.getName() + " (id=" + eventToEdit.getId() + ")";
        model.addAttribute("title",title);
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description, String contactEmail,
                                  String location, boolean regRequired, int numberAttendees){
       //EventData.edit(eventId,name,description,contactEmail, location, regRequired, numberAttendees);
       return "redirect:";
    }
    */


}
