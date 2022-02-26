(define (problem toy02)

    (:domain snowman_basic_adl_snowmen_1)

    (:objects
        right - direction
        left - direction
        up - direction
        down - direction
        ball_0 - ball
        ball_1 - ball
        ball_2 - ball
        loc_1_1 - location
        loc_1_2 - location
        loc_1_3 - location
        loc_1_4 - location
    )

    (:init
        (= (total-cost) 0)
        (next loc_1_1 loc_1_2 up)
        (next loc_1_2 loc_1_3 up)
        (next loc_1_2 loc_1_1 down)
        (next loc_1_3 loc_1_4 up)
        (next loc_1_3 loc_1_2 down)
        (next loc_1_4 loc_1_3 down)
        (character_at loc_1_1)
        (ball_at ball_0 loc_1_3)
        (ball_size_small ball_0)
        (ball_at ball_1 loc_1_4)
        (ball_size_large ball_1)
        (ball_at ball_2 loc_1_4)
        (ball_size_medium ball_2)
        (occupancy loc_1_3)
        (occupancy loc_1_4)
    )

    (:goal
        (and (goal))
    )

    (:metric minimize (total-cost))
)