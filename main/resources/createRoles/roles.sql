CREATE ROLE admin_bd
GRANT CREATE SESSION TO admin_bd
grant all PRIVILEGES to admin_bd
create role admin_hr
grant create session to admin_hr
grant select any table to admin_hr
grant insert, update, delete on "BUROWORKERS" to admin_hr
grant insert, update, delete on "CASHIERS" to admin_hr
grant insert, update, delete on "DISPETCHERS" to admin_hr
grant insert, update, delete on "SECURITIES" to admin_hr
grant insert, update, delete on "TEHWORKERS" to admin_hr
grant insert, update, delete on "PILOTS" to admin_hr
grant insert, update, delete on "WORKERS" to admin_hr
grant insert, update, delete on "TRIPS" to admin_hr
grant insert, update, delete on "TIMETABLE" to admin_hr
create role cashier_r
grant create session to cashier_r
grant select any table to cashier_r
grant insert, update, delete on "PASSENGERS" to cashier_r
grant insert, update, delete on "RESERVETICKETS" to cashier_r
grant insert, update, delete on "TICKETS" to cashier_r
create role technic
grant create session to technic
grant select any table to technic
grant insert, update, delete on "TECHNICALINSPECTION" to technic
create role passenger
grant create session to passenger
grant select any table to passenger