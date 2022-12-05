package scr.Viewer.Camera;

import scr.Model.Characters.Transform;

public class CameraMag{
    public CameraMove cameraMove;

    public CameraMag() {

        cameraMove = new CameraMove();
    }


    public void cameraMoving(Transform transform)
    {
        if(transform.xPos > cameraMove.mapRange)
        {
            cameraMove.mapMoving =  (transform.xPos-cameraMove.mapRange) ;
        }


    }
}
