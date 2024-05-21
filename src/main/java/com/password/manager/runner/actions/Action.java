package com.password.manager.runner.actions;

import com.password.manager.runner.Command;

public interface Action {

    void perform();

    Command getSupportedCommand();
}
