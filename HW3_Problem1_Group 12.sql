--We decided to choose the movie table and search keys mname 
--and genre for indexing. We did this because most of the queries 
--from the previous homework referenced the movie table and each movies genre.

--The index for mname is a primary index because it is built on the search key 
--of the main data table in which case it is the primary key for that table. 
--We also used genre as an index but it is a secondary index as it built on 
--other attributes on the table, in this case being the movie names. 

--We decided to rerun all queries that referenced the movie table as 
--well as the ones containing the where clause condition genre. These being 
--queries 2,3,6 and 9 from problem 2 of homework 2.

--Indexing statements
create unique index mname_index on Movie(mname)
create index genre_index on Movie(genre)

--2
select mname
from Movie
where genre = 'Action'

--3
select genre, avg(minutes) as avg_mins
from Movie m
group by genre

--6
select mname
from Movie
where genre = 'comedy'
union
select distinct mname  
from Acted 
group by mname
having COUNT(*) > 1

--9
delete from Movie 
where 1970 <= release_year and release_year <= 1989
