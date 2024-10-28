package com.arrelin.front.views.todo;

import com.arrelin.front.entity.ToDo;
import com.arrelin.front.repository.ToDoRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;


@Route("todo")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ToDoView extends VerticalLayout {

    private static final Logger logger = Logger.getLogger(ToDoView.class.getName());

    private final ToDoRepository toDoRepository;
    private VerticalLayout todosList = new VerticalLayout();

    @Autowired
    public ToDoView(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
        var taskField = new TextField();
        var addButton = new Button("Add");
        var clearButton = new Button("Clear");
        var backButton = new Button("Back", event -> getUI().ifPresent(ui -> ui.navigate("/")));

        addButton.addClickListener(click -> {
            String task = taskField.getValue();
            if (!task.isEmpty()) {
                ToDo todo = new ToDo();
                todo.setTask(task);
                todo.setCompleted(false);
                toDoRepository.save(todo);
                addTodoToList(todo);
                taskField.clear();
            }
        });

        clearButton.addClickListener(click -> {
            List<ToDo> todos = toDoRepository.findAll();
            todos.stream()
                    .filter(ToDo::isCompleted)
                    .forEach(toDoRepository::delete);
            todosList.removeAll(); // Clear the UI list before reloading
            loadTodos();
        });

        addButton.addClickShortcut(Key.ENTER);

        add(
                new H1("To-Do"),
                todosList,
                new HorizontalLayout(taskField, addButton, clearButton, backButton)
        );

        loadTodos();
    }

    @PostConstruct
    private void loadTodos() {
        todosList.removeAll(); // Clear the UI list before adding tasks
        List<ToDo> todos = toDoRepository.findAll();
        logger.info("Loaded todos: " + todos.size());
        for (ToDo todo : todos) {
            addTodoToList(todo);
        }
    }

    private void addTodoToList(ToDo todo) {
        Checkbox checkBox = new Checkbox(todo.getTask(), todo.isCompleted());
        checkBox.addValueChangeListener(event -> {
            todo.setCompleted(event.getValue());
            toDoRepository.save(todo);
        });
        todosList.add(checkBox);
    }
}