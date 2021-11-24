package techmaster.lesson8.controller;

import java.io.IOException;
import java.util.Base64;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import techmaster.lesson8.controller.Model.View;
import techmaster.lesson8.model.StaffModel;
import techmaster.lesson8.service.iService;

@Controller
public class StaffController {

    @Autowired
    iService<StaffModel> staffService;

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {
        model.addAttribute("active", "index");
        return "index";
    }

    @GetMapping(value = {"/list"})
    public String getListPage(Model model,
            @RequestParam(required = false, value = "limit") Integer limit,
            @RequestParam(required = false, value = "offset") Integer offset,
            @RequestParam(required = false, value = "move") String move) {
        View view = new View();
        view.setLimit(limit == null ? 10 : limit);
        view.setOffset(offset == null ? 0 : offset);
        if (move != null) {
            switch (move) {
                case "Prev":
                    int newOffset = view.getOffset() - view.getLimit();
                    view.setOffset(newOffset < 0 ? 0 : newOffset);
                    break;
                case "Next":
                    view.setOffset(view.getOffset() + view.getLimit());
                    break;
            }
        }
        model.addAttribute("addNew", false);
        model.addAttribute("list", staffService.getList(view.getLimit(), view.getOffset()));
        model.addAttribute("active", "list");
        model.addAttribute("view", view);
        return "list";
    }


    @GetMapping("/view_{id}")
    public String getViewPage(@PathVariable(value = "id") Long id, Model model) {
        StaffModel staffModel = staffService.getByID(id);
        boolean hasAvatar = staffModel.getAvatarBase64() != null ? true
                : false;
        model.addAttribute("person", staffModel);
        model.addAttribute("hasAvatar", hasAvatar);
        model.addAttribute("active", "view");
        return "view";
    }

    @GetMapping("/edit_{id}")
    public String getEditPage(@PathVariable(value = "id") Long id, Model model)
            throws DataAccessException {
        StaffModel staffModel = staffService.getByID(id);

        model.addAttribute("person", staffModel);
        model.addAttribute("hasAvatar", staffModel.getAvatar() != null ? true : false);
        model.addAttribute("active", "view");
        return "edit";
    }

    @PostMapping("/edit_{id}")
    public String postEditPage(@PathVariable(value = "id") Long id,
            @Valid @ModelAttribute(value = "person") StaffModel staffModel,
            BindingResult br, Model model)
            throws IOException {
        String getReturn;
        boolean hasAvatar = false;
        if (staffModel.getAvatar().length == 0) {
            staffModel.setAvatar(null);
        }
        if (br.hasErrors()) {
            if (staffModel.getAvatar() != null) {
                hasAvatar = true;
                staffModel.setAvatarBase64(Base64.getEncoder().encodeToString(
                        staffModel.getAvatar()));
            }
            model.addAttribute("person", staffModel);
            model.addAttribute("active", "view");
            model.addAttribute("hasAvatar", hasAvatar);
            return "edit";
        } else {
            getReturn = "redirect:" + "/view_" + String.valueOf(staffModel.getId());
            if (!staffModel.getAvatarUpload().isEmpty()) {
                staffModel.setAvatar(staffModel.getAvatarUpload().getBytes());
            }
            staffService.update(staffModel.getId(), staffModel);
            return getReturn;
        }
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        StaffModel staffModel = new StaffModel();
        model.addAttribute("newPerson", staffModel);
        model.addAttribute("active", "add");
        return "add";
    }

    @PostMapping("/add")
    public String postAddPage(
            @Valid @ModelAttribute(value = "newPerson") StaffModel staffModel,
            BindingResult br, Model model)
            throws IOException {
        boolean hasAvatar = false;
        if (staffModel.getAvatar().length == 0) {
            staffModel.setAvatar(null);
        }
        if (br.hasErrors()) {
            if (staffModel.getAvatar() != null) {
                hasAvatar = true;
                staffModel.setAvatarBase64(Base64.getEncoder().encodeToString(
                        staffModel.getAvatar()));
            }
            model.addAttribute("newPerson", staffModel);
            model.addAttribute("active", "add");
            model.addAttribute("hasAvatar", hasAvatar);
            return "add";
        } else {
            if (staffModel.getAvatarUpload() != null) {
                staffModel.setAvatar(staffModel.getAvatarUpload().getBytes());
            }
            staffService.add(staffModel);
            model.addAttribute("addNew", true);
            return "redirect:/list";
        }
    }

    @GetMapping("/delete_{id}")
    public String getDeletePage(@PathVariable(value = "id") Long id, Model model) {
        StaffModel staffModel = staffService.getByID(id);
        boolean hasAvatar =
                staffModel.getAvatar() != null || staffModel.getAvatarBase64() != null ? true
                        : false;
        model.addAttribute("person", staffModel);
        model.addAttribute("hasAvatar", hasAvatar);
        model.addAttribute("active", "delete");
        return "delete";
    }

    @PostMapping("/delete_{id}")
    public String postDeletePage(@PathVariable(value = "id") Long id) {
        staffService.delete(id);
        return "redirect:/list";
    }

}