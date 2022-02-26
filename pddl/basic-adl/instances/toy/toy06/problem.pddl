(define (problem toy06)

    (:domain snowman_basic_adl_snowmen_2)

    (:objects
        right - direction
        left - direction
        up - direction
        down - direction
        ball_0 - ball
        ball_1 - ball
        ball_2 - ball
        ball_3 - ball
        ball_4 - ball
        ball_5 - ball
        loc_1_1 - location
        loc_1_2 - location
        loc_1_3 - location
        loc_2_1 - location
        loc_2_2 - location
        loc_2_3 - location
        loc_3_1 - location
        loc_3_2 - location
        loc_3_3 - location
    )

    (:init
        (= (total-cost) 0)
        (next loc_1_1 loc_2_1 right)
        (next loc_1_1 loc_1_2 up)
        (next loc_1_2 loc_2_2 right)
        (next loc_1_2 loc_1_3 up)
        (next loc_1_2 loc_1_1 down)
        (next loc_1_3 loc_2_3 right)
        (next loc_1_3 loc_1_2 down)
        (next loc_2_1 loc_3_1 right)
        (next loc_2_1 loc_1_1 left)
        (next loc_2_1 loc_2_2 up)
        (next loc_2_2 loc_3_2 right)
        (next loc_2_2 loc_1_2 left)
        (next loc_2_2 loc_2_3 up)
        (next loc_2_2 loc_2_1 down)
        (next loc_2_3 loc_3_3 right)
        (next loc_2_3 loc_1_3 left)
        (next loc_2_3 loc_2_2 down)
        (next loc_3_1 loc_2_1 left)
        (next loc_3_1 loc_3_2 up)
        (next loc_3_2 loc_2_2 left)
        (next loc_3_2 loc_3_3 up)
        (next loc_3_2 loc_3_1 down)
        (next loc_3_3 loc_2_3 left)
        (next loc_3_3 loc_3_2 down)
        (character_at loc_1_1)
        (ball_at ball_0 loc_1_2)
        (ball_size_small ball_0)
        (ball_at ball_1 loc_1_3)
        (ball_size_large ball_1)
        (ball_at ball_2 loc_1_3)
        (ball_size_medium ball_2)
        (ball_at ball_3 loc_3_2)
        (ball_size_small ball_3)
        (ball_at ball_4 loc_3_3)
        (ball_size_large ball_4)
        (ball_at ball_5 loc_3_3)
        (ball_size_medium ball_5)
        (occupancy loc_1_2)
        (occupancy loc_1_3)
        (occupancy loc_3_2)
        (occupancy loc_3_3)
    )

    (:goal
        (and (goal))
    )

    (:metric minimize (total-cost))
)