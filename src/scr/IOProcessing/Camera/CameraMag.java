package scr.IOProcessing.Camera;

import scr.Model.Characters.Position.Transform;

public class CameraMag{
    public CameraMove cameraMove;

    public CameraMag() {

        cameraMove = new CameraMove();
    }


    public void cameraMoving(Transform transform)
    {
        if(transform.xPos > cameraMove.mapRange)
        {
            cameraMove.mapMoving =  (transform.xPos-cameraMove.mapRange);
        }


    }
}
