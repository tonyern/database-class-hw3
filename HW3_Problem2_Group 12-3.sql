--SQL Statement to calculate years_of_experience for option 1
GO
CREATE PROCEDURE exp_avg
    --2 variables
    @age_lower INT, @age_upper INT
AS
BEGIN
    --Select the average years of experiences from the Performer Table where age is +/- 10 years of the age. Then return that value
    RETURN (SELECT AVG(years_of_experience) FROM Performer WHERE age BETWEEN @age_lower AND @age_upper )
    
END
--SQL Statement to calculate years_of_experiences for option 2
GO
CREATE PROCEDURE exp_avg2
    --1 variable
    @did INT
AS
BEGIN
    --Select the movies from the given did, then select the pid of all actors who acted in the movie, then calculate the average years_of_experiences of those pid values in the Performer Table. Then return
    RETURN (SELECT AVG (years_of_experience)
    FROM Performer
    WHERE pid in
        (SELECT pid FROM Acted WHERE mname in (SELECT mname FROM movie WHERE did = @did)))
    
END
--SQL Statement to insert Performer into the Table
GO
CREATE PROCEDURE insert1
--4 variables for the columns
@pid INT,
@pname VARCHAR(30),
@years INT,
@age INT
AS
BEGIN
    INSERT INTO Performer (pid, pname, years_of_experience, age)
    VALUES (@pid, @pname, @years, @age);
END

