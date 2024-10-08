/*
 * RGraphicsDevDescVersions.hpp
 *
 * Copyright (C) 2022 by Posit Software, PBC
 *
 * Unless you have received this program directly from Posit Software pursuant
 * to the terms of a commercial license agreement with Posit Software, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

#ifndef R_SESSION_GRAPHICS_DEV_DESC_VERSIONS_HPP
#define R_SESSION_GRAPHICS_DEV_DESC_VERSIONS_HPP

#include <r/RInternal.hpp>

#define R_USE_PROTOTYPES 1
#include <R_ext/GraphicsEngine.h>
#include <R_ext/GraphicsDevice.h>

extern "C" {

struct DevDescVersion5
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);

   // end of devDescUniversal

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;
   char reserved[64];
};

struct DevDescVersion6
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);


   // dev_Raster and dev_Cap added in version 6
   void (*raster)(unsigned int *raster, int w, int h,
                double x, double y,
                double width, double height,
                double rot,
                Rboolean interpolate,
                const pGEcontext gc, pDevDesc dd);
   SEXP (*cap)(pDevDesc dd);

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;
   char reserved[64];
};

struct DevDescVersion7
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);


   // dev_Raster and dev_Cap added in version 6
   void (*raster)(unsigned int *raster, int w, int h,
                double x, double y,
                double width, double height,
                double rot,
                Rboolean interpolate,
                const pGEcontext gc, pDevDesc dd);
   SEXP (*cap)(pDevDesc dd);

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;

   // eventEnv and eventHelper added in version 7
   SEXP eventEnv;
   void (*eventHelper)(pDevDesc dd, int code);

   char reserved[64];
};

struct DevDescVersion8
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);

   // dev_Path added in version 8
   void (*path)(double *x, double *y,
                  int npoly, int *nper,
                  Rboolean winding,
                  const pGEcontext gc, pDevDesc dd);

   // dev_Raster and dev_Cap added in version 6
   void (*raster)(unsigned int *raster, int w, int h,
                double x, double y,
                double width, double height,
                double rot,
                Rboolean interpolate,
                const pGEcontext gc, pDevDesc dd);
   SEXP (*cap)(pDevDesc dd);

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;

   // eventEnv and eventHelper added in version 7
   SEXP eventEnv;
   void (*eventHelper)(pDevDesc dd, int code);

   char reserved[64];
};

// NOTE: compatible with graphics engine versions 9, 10, 11
struct DevDescVersion9
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);


   // dev_Path added in version 8
   void (*path)(double *x, double *y,
                  int npoly, int *nper,
                  Rboolean winding,
                  const pGEcontext gc, pDevDesc dd);

   // dev_Raster and dev_Cap added in version 6
   void (*raster)(unsigned int *raster, int w, int h,
                double x, double y,
                double width, double height,
                double rot,
                Rboolean interpolate,
                const pGEcontext gc, pDevDesc dd);
   SEXP (*cap)(pDevDesc dd);

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;

   // eventEnv and eventHelper added in version 7
   SEXP eventEnv;
   void (*eventHelper)(pDevDesc dd, int code);

   // holdFlush and have* added in version 9 (R 2.14)
   int (*holdflush)(pDevDesc dd, int level);
   int haveTransparency;
   int haveTransparentBg;
   int haveRaster;
   int haveCapture, haveLocator;

   char reserved[64];
};

struct DevDescVersion12
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean canGenIdle; // version 12
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);


   // dev_Path added in version 8
   void (*path)(double *x, double *y,
                  int npoly, int *nper,
                  Rboolean winding,
                  const pGEcontext gc, pDevDesc dd);

   // dev_Raster and dev_Cap added in version 6
   void (*raster)(unsigned int *raster, int w, int h,
                double x, double y,
                double width, double height,
                double rot,
                Rboolean interpolate,
                const pGEcontext gc, pDevDesc dd);
   SEXP (*cap)(pDevDesc dd);

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;

   // eventEnv and eventHelper added in version 7
   SEXP eventEnv;
   void (*eventHelper)(pDevDesc dd, int code);

   // holdFlush and have* added in version 9 (R 2.14)
   int (*holdflush)(pDevDesc dd, int level);
   int haveTransparency;
   int haveTransparentBg;
   int haveRaster;
   int haveCapture, haveLocator;

   char reserved[64];
};

struct DevDescVersion14
{
   double left;
   double right;
   double bottom;
   double top;
   double clipLeft;
   double clipRight;
   double clipBottom;
   double clipTop;
   double xCharOffset;
   double yCharOffset;
   double yLineBias;
   double ipr[2];
   double cra[2];
   double gamma;
   Rboolean canClip;
   Rboolean canChangeGamma;
   int canHAdj;
   double startps;
   int startcol;
   int startfill;
   int startlty;
   int startfont;
   double startgamma;
   void *deviceSpecific;
   Rboolean displayListOn;
   Rboolean canGenMouseDown;
   Rboolean canGenMouseMove;
   Rboolean canGenMouseUp;
   Rboolean canGenKeybd;
   Rboolean canGenIdle; // version 12
   Rboolean gettingEvent;

   void (*activate)(const pDevDesc );
   void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
   void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
   void (*close)(pDevDesc dd);
   void (*deactivate)(pDevDesc );
   Rboolean (*locator)(double *x, double *y, pDevDesc dd);
   void (*line)(double x1, double y1, double x2, double y2,
       const pGEcontext gc, pDevDesc dd);
   void (*metricInfo)(int c, const pGEcontext gc,
             double* ascent, double* descent, double* width,
             pDevDesc dd);
   void (*mode)(int mode, pDevDesc dd);
   void (*newPage)(const pGEcontext gc, pDevDesc dd);
   void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
   void (*rect)(double x0, double y0, double x1, double y1,
       const pGEcontext gc, pDevDesc dd);


   // dev_Path added in version 8
   void (*path)(double *x, double *y,
                  int npoly, int *nper,
                  Rboolean winding,
                  const pGEcontext gc, pDevDesc dd);

   // dev_Raster and dev_Cap added in version 6
   void (*raster)(unsigned int *raster, int w, int h,
                double x, double y,
                double width, double height,
                double rot,
                Rboolean interpolate,
                const pGEcontext gc, pDevDesc dd);
   SEXP (*cap)(pDevDesc dd);

   void (*size)(double *left, double *right, double *bottom, double *top,
    pDevDesc dd);
   double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
   void (*text)(double x, double y, const char *str, double rot,
    double hadj, const pGEcontext gc, pDevDesc dd);
   void (*onExit)(pDevDesc dd);
   SEXP (*getEvent)(SEXP, const char *);
   Rboolean (*newFrameConfirm)(pDevDesc dd);

   Rboolean hasTextUTF8; /* and strWidthUTF8 */
   void (*textUTF8)(double x, double y, const char *str, double rot,
        double hadj, const pGEcontext gc, pDevDesc dd);
   double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
   Rboolean wantSymbolUTF8;
   Rboolean useRotatedTextInContour;

   // eventEnv and eventHelper added in version 7
   SEXP eventEnv;
   void (*eventHelper)(pDevDesc dd, int code);

   // holdFlush and have* added in version 9 (R 2.14)
   int (*holdflush)(pDevDesc dd, int level);
   int haveTransparency;
   int haveTransparentBg;
   int haveRaster;
   int haveCapture, haveLocator;
   
   // below added in version 14 (R 4.1.0)
   SEXP (*setPattern)(SEXP pattern, pDevDesc dd);
   void (*releasePattern)(SEXP ref, pDevDesc dd);
   
   SEXP (*setClipPath)(SEXP path, SEXP ref, pDevDesc dd);
   void (*releaseClipPath)(SEXP ref, pDevDesc dd);
   
   SEXP (*setMask)(SEXP path, SEXP ref, pDevDesc dd);
   void (*releaseMask)(SEXP ref, pDevDesc dd);
   
   int deviceVersion;
   Rboolean deviceClip;

   char reserved[64];
};

struct DevDescVersion15
{
    double left;
    double right;
    double bottom;
    double top;
    double clipLeft;
    double clipRight;
    double clipBottom;
    double clipTop;
    double xCharOffset;
    double yCharOffset;
    double yLineBias;
    double ipr[2];
    double cra[2];
    double gamma;
    Rboolean canClip;
    Rboolean canChangeGamma;
    int canHAdj;
    double startps;
    int startcol;
    int startfill;
    int startlty;
    int startfont;
    double startgamma;
    void *deviceSpecific;
    Rboolean displayListOn;
    Rboolean canGenMouseDown;
    Rboolean canGenMouseMove;
    Rboolean canGenMouseUp;
    Rboolean canGenKeybd;
    Rboolean canGenIdle; // version 12
    Rboolean gettingEvent;

    void (*activate)(const pDevDesc );
    void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
    void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
    void (*close)(pDevDesc dd);
    void (*deactivate)(pDevDesc );
    Rboolean (*locator)(double *x, double *y, pDevDesc dd);
    void (*line)(double x1, double y1, double x2, double y2,
                 const pGEcontext gc, pDevDesc dd);
    void (*metricInfo)(int c, const pGEcontext gc,
                       double* ascent, double* descent, double* width,
                       pDevDesc dd);
    void (*mode)(int mode, pDevDesc dd);
    void (*newPage)(const pGEcontext gc, pDevDesc dd);
    void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
    void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
    void (*rect)(double x0, double y0, double x1, double y1,
                 const pGEcontext gc, pDevDesc dd);


    // dev_Path added in version 8
    void (*path)(double *x, double *y,
                 int npoly, int *nper,
                 Rboolean winding,
                 const pGEcontext gc, pDevDesc dd);

    // dev_Raster and dev_Cap added in version 6
    void (*raster)(unsigned int *raster, int w, int h,
                   double x, double y,
                   double width, double height,
                   double rot,
                   Rboolean interpolate,
                   const pGEcontext gc, pDevDesc dd);
    SEXP (*cap)(pDevDesc dd);

    void (*size)(double *left, double *right, double *bottom, double *top,
                 pDevDesc dd);
    double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
    void (*text)(double x, double y, const char *str, double rot,
                 double hadj, const pGEcontext gc, pDevDesc dd);
    void (*onExit)(pDevDesc dd);
    SEXP (*getEvent)(SEXP, const char *);
    Rboolean (*newFrameConfirm)(pDevDesc dd);

    Rboolean hasTextUTF8; /* and strWidthUTF8 */
    void (*textUTF8)(double x, double y, const char *str, double rot,
                     double hadj, const pGEcontext gc, pDevDesc dd);
    double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
    Rboolean wantSymbolUTF8;
    Rboolean useRotatedTextInContour;

    // eventEnv and eventHelper added in version 7
    SEXP eventEnv;
    void (*eventHelper)(pDevDesc dd, int code);

    // holdFlush and have* added in version 9 (R 2.14)
    int (*holdflush)(pDevDesc dd, int level);
    int haveTransparency;
    int haveTransparentBg;
    int haveRaster;
    int haveCapture, haveLocator;

    // below added in version 14 (R 4.1.0)
    SEXP (*setPattern)(SEXP pattern, pDevDesc dd);
    void (*releasePattern)(SEXP ref, pDevDesc dd);

    SEXP (*setClipPath)(SEXP path, SEXP ref, pDevDesc dd);
    void (*releaseClipPath)(SEXP ref, pDevDesc dd);

    SEXP (*setMask)(SEXP path, SEXP ref, pDevDesc dd);
    void (*releaseMask)(SEXP ref, pDevDesc dd);

    int deviceVersion;
    Rboolean deviceClip;

    // below added in version 15 (R 4.2.0)
    SEXP (*defineGroup)(SEXP source, int op, SEXP destination, pDevDesc dd);
    void (*useGroup)(SEXP ref, SEXP trans, pDevDesc dd);
    void (*releaseGroup)(SEXP ref, pDevDesc dd);

    void (*stroke)(SEXP path, const pGEcontext gc, pDevDesc dd);
    void (*fill)(SEXP path, int rule, const pGEcontext gc, pDevDesc dd);
    void (*fillStroke)(SEXP path, int rule, const pGEcontext gc, pDevDesc dd);

    SEXP (*capabilities)(SEXP cap);

    char reserved[64];
};

struct DevDescVersion16
{
    double left;
    double right;
    double bottom;
    double top;
    double clipLeft;
    double clipRight;
    double clipBottom;
    double clipTop;
    double xCharOffset;
    double yCharOffset;
    double yLineBias;
    double ipr[2];
    double cra[2];
    double gamma;
    Rboolean canClip;
    Rboolean canChangeGamma;
    int canHAdj;
    double startps;
    int startcol;
    int startfill;
    int startlty;
    int startfont;
    double startgamma;
    void *deviceSpecific;
    Rboolean displayListOn;
    Rboolean canGenMouseDown;
    Rboolean canGenMouseMove;
    Rboolean canGenMouseUp;
    Rboolean canGenKeybd;
    Rboolean canGenIdle; // version 12
    Rboolean gettingEvent;

    void (*activate)(const pDevDesc );
    void (*circle)(double x, double y, double r, const pGEcontext gc, pDevDesc dd);
    void (*clip)(double x0, double x1, double y0, double y1, pDevDesc dd);
    void (*close)(pDevDesc dd);
    void (*deactivate)(pDevDesc );
    Rboolean (*locator)(double *x, double *y, pDevDesc dd);
    void (*line)(double x1, double y1, double x2, double y2,
                 const pGEcontext gc, pDevDesc dd);
    void (*metricInfo)(int c, const pGEcontext gc,
                       double* ascent, double* descent, double* width,
                       pDevDesc dd);
    void (*mode)(int mode, pDevDesc dd);
    void (*newPage)(const pGEcontext gc, pDevDesc dd);
    void (*polygon)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
    void (*polyline)(int n, double *x, double *y, const pGEcontext gc, pDevDesc dd);
    void (*rect)(double x0, double y0, double x1, double y1,
                 const pGEcontext gc, pDevDesc dd);


    // dev_Path added in version 8
    void (*path)(double *x, double *y,
                 int npoly, int *nper,
                 Rboolean winding,
                 const pGEcontext gc, pDevDesc dd);

    // dev_Raster and dev_Cap added in version 6
    void (*raster)(unsigned int *raster, int w, int h,
                   double x, double y,
                   double width, double height,
                   double rot,
                   Rboolean interpolate,
                   const pGEcontext gc, pDevDesc dd);
    SEXP (*cap)(pDevDesc dd);

    void (*size)(double *left, double *right, double *bottom, double *top,
                 pDevDesc dd);
    double (*strWidth)(const char *str, const pGEcontext gc, pDevDesc dd);
    void (*text)(double x, double y, const char *str, double rot,
                 double hadj, const pGEcontext gc, pDevDesc dd);
    void (*onExit)(pDevDesc dd);
    SEXP (*getEvent)(SEXP, const char *);
    Rboolean (*newFrameConfirm)(pDevDesc dd);

    Rboolean hasTextUTF8; /* and strWidthUTF8 */
    void (*textUTF8)(double x, double y, const char *str, double rot,
                     double hadj, const pGEcontext gc, pDevDesc dd);
    double (*strWidthUTF8)(const char *str, const pGEcontext gc, pDevDesc dd);
    Rboolean wantSymbolUTF8;
    Rboolean useRotatedTextInContour;

    // eventEnv and eventHelper added in version 7
    SEXP eventEnv;
    void (*eventHelper)(pDevDesc dd, int code);

    // holdFlush and have* added in version 9 (R 2.14)
    int (*holdflush)(pDevDesc dd, int level);
    int haveTransparency;
    int haveTransparentBg;
    int haveRaster;
    int haveCapture, haveLocator;

    // below added in version 14 (R 4.1.0)
    SEXP (*setPattern)(SEXP pattern, pDevDesc dd);
    void (*releasePattern)(SEXP ref, pDevDesc dd);

    SEXP (*setClipPath)(SEXP path, SEXP ref, pDevDesc dd);
    void (*releaseClipPath)(SEXP ref, pDevDesc dd);

    SEXP (*setMask)(SEXP path, SEXP ref, pDevDesc dd);
    void (*releaseMask)(SEXP ref, pDevDesc dd);

    int deviceVersion;
    Rboolean deviceClip;

    // below added in version 15 (R 4.2.0)
    SEXP (*defineGroup)(SEXP source, int op, SEXP destination, pDevDesc dd);
    void (*useGroup)(SEXP ref, SEXP trans, pDevDesc dd);
    void (*releaseGroup)(SEXP ref, pDevDesc dd);

    void (*stroke)(SEXP path, const pGEcontext gc, pDevDesc dd);
    void (*fill)(SEXP path, int rule, const pGEcontext gc, pDevDesc dd);
    void (*fillStroke)(SEXP path, int rule, const pGEcontext gc, pDevDesc dd);

    SEXP (*capabilities)(SEXP cap);

    // below added in version 16 (R 4.3.0)
    void (*glyph)(int n, int *glyphs, double *x, double *y, 
                  SEXP font, double size,
                  int colour, double rot, pDevDesc dd);

    char reserved[64];
};
} // extern "C"

#endif // R_SESSION_GRAPHICS_DEV_DESC_VERSIONS_HPP

