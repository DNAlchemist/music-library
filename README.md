# Music Library

Java library for music downloads

### download the track

    MusicLibrary musicLibrary = MusicLibrary.createDefaultLibrary(host);
    musicLibrary.searchTrack("Kasabian", "Underdog")
                .ifPresent(track -> {
                    try(InputStream in = musicLibrary.fetchInputStream(track)) {
                        Files.copy(in, Paths.get("Kasabian - Underdog.mp3"))
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                
### guess the track

    MusicGuesser lib = MusicGuesser.createDefaultGuesser(host);
    List<String> searchResult = lib.suggest("Robert Jo");
    assert searchResult.contains("Robert johnson - little queen of spades");
