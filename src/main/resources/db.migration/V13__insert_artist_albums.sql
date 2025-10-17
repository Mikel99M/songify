INSERT INTO artist_albums (albums_id, artsts_id)
VALUES (1, 1),   -- Echoes of Eternity → Aurora Sky
       (2, 2),   -- Neon Skyline → Neon Rivers
       (3, 3),   -- Midnight Horizons → Echo Drift
       (4, 4),   -- Fragments of Light → Luna Waves
       (5, 5),   -- Waves of Silence → The Midnight Parade
       (6, 6),   -- Parallel Dreams → Crimson Skyline
       (7, 7),   -- Celestial Motion → Solar Minds
       (8, 8),   -- The Lost Frequencies → Electric Nomads
       (9, 9),   -- Shadows & Sparks → Golden Frame
       (10, 10), -- Tomorrow’s Dawn → Velvet Horizon

-- extra connections (for variety, showing many-to-many)
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 1),
       (5, 6),
       (6, 7),
       (7, 8),
       (8, 9),
       (9, 10);
