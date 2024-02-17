package com.derfiedev;

import java.time.LocalDateTime;

public record ServerEntry(
    String host,
    int port,
    int timeout,
    String favicon,
    String motd,
    String version,
    int players_online,
    int players_max,
    int protocol,
    LocalDateTime first_found,
    LocalDateTime last_updated
) {}
