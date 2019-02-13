% eyetracking software

% could use this to find things like time spent looking at each object


% loop through data post-processing for eye-tracking software.
datasize = 1000;
numObjects = 10;
seen = FALSE;
for i = 0:datasize
    % arrived at a datapoint
    x = 10;
    y = 10;
    z = 10;
    yaw = 45;
    pitch = 45;
    roll = 45;
    
    
    
    
    for j = 0:numObjects
        % calculate pitch and yaw necessary to be looking at obj
        s.X = 15;
        s.Y = 15;
        s.Z = 15;
        s.yaw = 45;
        s.pitch = 45;
        s.roll = 45;
        
        %convert difference between 2 objects
        deltax = x - s.X;
        deltay = y - s.Y;
        deltaz = z - s.Z;
        
        % convert from rectangular coordinates to polar coordinates
        yaw2 = atan2(deltaz, deltax);
        pitch2 = atan2( sqrt(deltaz * deltaz + deltax * deltax), deltay ) + pi; 
        
        if yaw2 == yaw
            if pitch2 == pitch
                seen = TRUE;
            end
        else 
            seen = FALSE;   
        end
    end
        
end


            
            
              
        
    

