package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Command;

public record DeleteOfferByIdCommand(long id) implements Command { }
